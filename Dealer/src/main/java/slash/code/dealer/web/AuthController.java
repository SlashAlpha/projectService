package slash.code.dealer.web;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import slash.code.dealer.config.security.SecurityUti;
import slash.code.dealer.security.User;
import slash.code.dealer.services.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {


    UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/refreshtoken")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Integer userTime = 60 * 60 * 1000;
        Integer adminTime = 24 * 60 * 60 * 1000;
        List<String> emails = Arrays.stream(SecurityUti.apiUser(1)).collect(Collectors.toList());

        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {

                User user = userService.getUserDealer(SecurityUti.decodeJwt(authorizationHeader).getSubject());
                String accessToken = SecurityUti.tokenUtiJWT(user.getEmail(), null, user.getRoles(), emails.contains(user.getEmail()) ? adminTime : 0);
                new ObjectMapper().writeValue(response.getOutputStream(), SecurityUti.responseToken(response, accessToken, authorizationHeader.substring("Bearer ".length())));
            } catch (Exception e) {
                SecurityUti.except(e, response);
            }
        } else {
            throw new RuntimeException("Refresh token is missing");
        }

    }

}
