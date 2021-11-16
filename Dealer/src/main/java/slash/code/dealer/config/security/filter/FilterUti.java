package slash.code.dealer.config.security.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import slash.code.dealer.security.Role;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@UtilityClass
public class FilterUti {

    public static String pokerToken;

    public String tokenUtiJWT(String userEmail, Collection<GrantedAuthority> userRole, Collection<Role> roles, Integer tokenAvailability) {

        Algorithm algorithm = Algorithm.HMAC256("slash".getBytes());
        String token = JWT.create()
                .withSubject(userEmail)
                .withExpiresAt(new Date(System.currentTimeMillis() + tokenAvailability))
                .withIssuer("Slash")
                .withClaim("roles", !(userRole == null) ? userRole.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList())
                        : roles.stream().map(Role::getName).collect(Collectors.toList()))
                .sign(algorithm);
        return token;
    }

    public void authorizationUtiJWT(String authorizationHeader) {

        DecodedJWT decodedJWT = decodeJwt(authorizationHeader);
        String email = decodedJWT.getSubject();
        String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        stream(roles).forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role));

        });
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(email, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }

    public void except(Exception e, HttpServletResponse response) throws IOException {
        log.error("error logging in : {}", e.getMessage());
        response.setHeader("error", e.getMessage());
        response.setStatus(FORBIDDEN.value());
        Map<String, String> error = new HashMap<>();
        error.put("error message", e.getMessage());
        response.setContentType(APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), error);

    }

    public DecodedJWT decodeJwt(String authorizationHeader) {
        String token = authorizationHeader.substring("Bearer ".length());
        Algorithm algorithm = Algorithm.HMAC256("slash".getBytes());
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = verifier.verify(token);
        return decodedJWT;
    }

    public Map<String, String> responseToken(HttpServletResponse response, String accessToken, String refreshToken) {
        Map<String, String> tokens = new HashMap<>();
        tokens.put("access token", accessToken);
        tokens.put("refresh token", refreshToken);
        response.setContentType(APPLICATION_JSON_VALUE);
        return tokens;
    }

    public String[] apiUser(int choice) {
        String[] email = {"joshmartini@gmail.com", "juliabeer@outlook.com", "dinavodka@yahoo.com", "robertsinaloa@aol.com",
                "sophiaCarlyle@google.fr", "tobbypartypooper@smellmouth.com", "ratkevin@bossrating.com", "accountingtina@waytogoup.com", "jimmyObryan@novel&wine.com"};
        String[] password = {"érmlgkd", "rdmlkpskfd", "smlkfsmel", "peoifpoq", "emlfkmfl", "zepofezpofk", "zemflkezmf", "rorkmqrlkgr", "fqmlfqmfqemff"};
        if (choice == 1) {
            return email;
        } else if (choice == 2) {
            return password;
        }
        return null;
    }

}
