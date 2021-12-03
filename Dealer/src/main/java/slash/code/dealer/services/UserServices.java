package slash.code.dealer.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import slash.code.dealer.config.JmsConfig;
import slash.code.dealer.config.security.SecurityUti;
import slash.code.dealer.security.Role;
import slash.code.dealer.security.TokenDto;
import slash.code.dealer.security.User;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;


@Slf4j
@Transactional
@Service
public class UserServices implements UserService, UserDetailsService {

    public final static String LOGIN_PATH = "http://localhost:8081/api/login";
    private final ObjectMapper objectMapper;
    private final PasswordEncoder passwordEncoder;
    User userDealer;
    User userPoker = new User();
    RestTemplate restTemplate;
    JmsTemplate jmsTemplate;

    public UserServices(JmsTemplate jmsTemplate, RestTemplateBuilder restTemplate, ObjectMapper objectMapper, PasswordEncoder passwordEncoder) {
        this.restTemplate = restTemplate.build();
        this.passwordEncoder = passwordEncoder;
        this.objectMapper = objectMapper;
        this.jmsTemplate = jmsTemplate;
    }

    @Override
    public User getUserDealer(String email) {
        if (!email.equals(null) && email.equals(userDealer.getEmail())) {
            return this.userDealer;
        } else {
            return null;
        }
    }

    @Override
    public User getUserPoker() {
        return this.userPoker;
    }

    @Override
    @JmsListener(destination = JmsConfig.POKER_AUTHENTICATION)
    public void securedPokerApi(@Payload String code) {
        int username = Integer.parseInt(code.substring(0, 1));
        int password = Integer.parseInt(code.substring(2));
        String email = SecurityUti.apiUser(1)[username];
        String passW = SecurityUti.apiUser(2)[password];
        Role adminApi = new Role("ROLE_ADMIN");
        userPoker.getRoles().add(adminApi);
        userPoker = new User(email, passW, userPoker.getRoles());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> userSecurity = new LinkedMultiValueMap<String, String>();
        userSecurity.add("email", email);
        userSecurity.add("password", passW);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(userSecurity, headers);
        //  System.out.println(email + " " + passW);
        ResponseEntity response = restTemplate.exchange(LOGIN_PATH, HttpMethod.POST, request, String.class);
        String accessToken = response.getBody().toString();
        JsonElement element = SecurityUti.parse(accessToken);
        JsonObject jsonObject = element.getAsJsonObject();
        TokenDto tokens = new TokenDto(jsonObject.get("accessToken").toString().replaceAll("^\"+|\"+$", ""), jsonObject.get("refreshToken").toString());
        System.out.println(tokens);
        SecurityUti.pokerToken = tokens.getAccessToken();
        System.out.println("ac token" + tokens.getAccessToken());
        int userMail = (int) (Math.random() * 9);
        while (userMail == username) {
            userMail = (int) (Math.random() * 9);
        }
        int userPass = (int) (Math.random() * 9);
        email = SecurityUti.apiUser(1)[userMail];
        passW = SecurityUti.apiUser(2)[userPass];
        System.out.println("new user=  email : " + email + " pass : " + passW);
        userDealer = new User(email, passwordEncoder.encode(passW), userPoker.getRoles());

        jmsTemplate.convertAndSend(JmsConfig.DEALER_AUTHENTICATION, userMail + "-" + userPass);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization", "Bearer " + tokens.getAccessToken());
        HttpEntity entity = new HttpEntity(httpHeaders);
        ResponseEntity<String> refreshToken = restTemplate.exchange("http://localhost:8081/api/v1/auth/refreshtoken", HttpMethod.GET, entity, String.class);
        //  System.out.println("new Refresh token " + refreshToken.getBody());
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        if (!userDealer.getEmail().equals(email)) {
            log.error("user not found");
            throw new UsernameNotFoundException("user not found");
        } else {
            log.info("user found : {}", userDealer.getEmail());

            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            userDealer.getRoles().forEach(role -> {
                authorities.add(new SimpleGrantedAuthority(role.getName()));
            });
            return new org.springframework.security.core.userdetails.User(userDealer.getEmail(), userDealer.getPassword(), authorities);
        }
    }

    @Override
    public User saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return user;
    }

}
