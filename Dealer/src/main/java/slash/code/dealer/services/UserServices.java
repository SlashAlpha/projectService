package slash.code.dealer.services;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import slash.code.dealer.config.security.filter.FilterUti;
import slash.code.dealer.security.Role;
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
    public User getUserDealer() {
        return this.userDealer;
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
        String email = FilterUti.apiUser(1)[username];
        String passW = FilterUti.apiUser(2)[password];

        Role adminApi = new Role("ROLE_ADMIN");
        userPoker.getRoles().add(adminApi);
        userPoker = new User(email, passW, userPoker.getRoles());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> userSecurity = new LinkedMultiValueMap<String, String>();
        userSecurity.add("email", email);
        userSecurity.add("password", passW);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(userSecurity, headers);

        System.out.println(request);
        System.out.println(email + " " + passW);
        ResponseEntity tokenDto = restTemplate.exchange(LOGIN_PATH, HttpMethod.POST, request, String.class);

        FilterUti.pokerToken = tokenDto.getBody().toString();
        System.out.println(tokenDto.getBody().toString());
        userDealer = new User();
        int userMail = (int) (Math.random() * 9);
        int userPass = (int) (Math.random() * 9);
        email = FilterUti.apiUser(1)[userMail];
        passW = FilterUti.apiUser(2)[userPass];
        System.out.println("new user=  email : " + email + " pass : " + passW);
        userDealer = new User(email, passwordEncoder.encode(passW), userPoker.getRoles());
        jmsTemplate.convertAndSend(JmsConfig.DEALER_AUTHENTICATION, userMail + "-" + userPass);

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
