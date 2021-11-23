package slash.code.game.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import slash.code.game.config.security.SecurityUti;
import slash.code.game.service.UserService;
import slash.code.game.user.Role;
import slash.code.game.user.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Slf4j
@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;


    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok(userService.getUsers());
        //   return new ResponseEntity<List<User>>(userService.getUsers(), HttpStatus.OK);
    }

    @PostMapping("/newuser")
    public ResponseEntity<User> newUser(@RequestBody User user) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/auth/newuser").toUriString());

        return ResponseEntity.created(uri).body(userService.saveUser(user));
        //   return new ResponseEntity<List<User>>(userService.getUsers(), HttpStatus.OK);
    }

    @PostMapping("/newrole")
    public ResponseEntity<Role> newRole(@RequestBody Role role) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/auth/newrole").toUriString());
        return ResponseEntity.created(uri).body(userService.saveRole(role));
    }

    @PostMapping("/addroletouser")
    public ResponseEntity<?> userRole(@RequestBody RoleToUserForm form) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/auth/addroletouser").toUriString());
        userService.addRoleToUser(form.getEmail(), form.getRoleName());
        return ResponseEntity.ok().build();


    }

    @GetMapping("/refreshtoken")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Integer userTime = 60 * 60 * 1000;
        Integer adminTime = 24 * 60 * 60 * 1000;
        List<String> emails = Arrays.stream(SecurityUti.apiUser(1)).collect(Collectors.toList());
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                //    String refreshToken = authorizationHeader.substring("Bearer ".length());
                //        DecodedJWT decodedJWT = FilterUti.decodeJwt(authorizationHeader);
//                String email = decodedJWT.getSubject();
                User user = userService.getUser(SecurityUti.decodeJwt(authorizationHeader).getSubject());
                String accessToken = SecurityUti.tokenUtiJWT(user.getEmail(), null, user.getRoles(), emails.contains(user.getEmail()) ? adminTime : userTime);
//                        JWT.create()
//                        .withSubject(user.getEmail())
//                        .withExpiresAt(new Date(System.currentTimeMillis() + 60 * 60 * 1000))
//                        .withIssuer("Slash")
//                        .withClaim("roles", user.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
//                        .sign(algorithm);
//                Map<String, String> tokens = new HashMap<>();
//                tokens.put("access token", accessToken);
//                tokens.put("refresh token", authorizationHeader.substring("Bearer ".length()));
//                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), SecurityUti.responseToken(response, accessToken, authorizationHeader.substring("Bearer ".length())));
            } catch (Exception e) {
//                log.error("error logging in : {}", e.getMessage());
//                response.setHeader("error", e.getMessage());
//                response.setStatus(FORBIDDEN.value());
//                Map<String, String> error = new HashMap<>();
//                error.put("error message", e.getMessage());
//                response.setContentType(APPLICATION_JSON_VALUE);
                SecurityUti.except(e, response);
//                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }
        } else {
            throw new RuntimeException("Refresh token is missing");
        }

    }


}

@Data
class RoleToUserForm {
    private String email;
    private String roleName;

}
