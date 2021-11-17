package slash.code.game.service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.jms.annotation.JmsListener;
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
import slash.code.game.config.messaging.JmsConfig;
import slash.code.game.config.security.SecurityUti;
import slash.code.game.user.*;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@Transactional
@Slf4j
public class UserServices implements UserService, UserDetailsService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    public final static String LOGIN_PATH = "http://localhost:8080/api/login";
    private RestTemplate restTemplate;

    public UserServices(RestTemplateBuilder restTemplate, UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.restTemplate = restTemplate.build();
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            log.error("user not found");
            throw new UsernameNotFoundException("user not found");
        } else {
            log.info("user found : {}", user.getEmail() + " " + user.getPassword());
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
    }

    @Override
    public User saveUser(User user) {
        log.info("saving user : {} to the database", user.getEmail() + " " + user.getPassword());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public Role saveRole(Role role) {
        log.info("saving role : {} to the database", role.getName());
        return roleRepository.save(role);
    }


    @Override
    public void addRoleToUser(String email, String roleName) {
        log.info("saving role : {} to the user {}", roleName, email);
        userRepository.findByEmail(email).getRoles().add(roleRepository.findByName(roleName));
    }

    @Override
    public User getUser(String email) {
        log.info("fetching the user {}", email);
        return userRepository.findByEmail(email);
    }

    @Override
    public List<User> getUsers() {
        log.info("fetching all user");
        return userRepository.findAll();
    }

    @Override
    @JmsListener(destination = JmsConfig.DEALER_AUTHENTICATION)
    public void securityDealer(@Payload String code) {

        int username = Integer.parseInt(code.substring(0, 1));
        int password = Integer.parseInt(code.substring(2));
        String email = SecurityUti.apiUser(1)[username];
        String passW = SecurityUti.apiUser(2)[password];
        User dealerUser = User.builder().firstName("api").lastName("api").email(email).password(passW).build();
        saveUser(dealerUser);


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> userSecurity = new LinkedMultiValueMap<String, String>();
        userSecurity.add("email", email);
        userSecurity.add("password", passW);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(userSecurity, headers);
        System.out.println(email + " " + passW);
        ResponseEntity tokenDto = restTemplate.exchange(LOGIN_PATH, HttpMethod.POST, request, String.class);
        String accessToken = tokenDto.getBody().toString();
        Gson gson = new Gson();
        JsonObject jsonObject = new JsonParser().parse(accessToken).getAsJsonObject();
        TokenDto tokens = new TokenDto(jsonObject.get("access token").toString().replaceAll("^\"+|\"+$", ""), jsonObject.get("refresh token").toString());
        System.out.println(tokens);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization", "Bearer " + tokens.getAccessToken());
        HttpEntity entity = new HttpEntity(httpHeaders);
        ResponseEntity<String> refreshToken = restTemplate.exchange("http://localhost:8080/api/v1/auth/refreshtoken", HttpMethod.GET, entity, String.class);
        System.out.println("new Refresh token " + refreshToken.getBody());


    }


}
