package slash.code.game.config.security.filters;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;
import slash.code.game.config.security.SecurityUti;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.http.HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Slf4j
public class CustomAuthorizationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getServletPath().equals("/api/login") || request.getServletPath().equals("/refreshtoken")) {
            filterChain.doFilter(request, response);
        } else {
            String authorizationHeader = request.getHeader(AUTHORIZATION);
            request.getHeader(ACCESS_CONTROL_ALLOW_ORIGIN);
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                try {
//                    String token = authorizationHeader.substring("Bearer ".length());
//                    Algorithm algorithm = Algorithm.HMAC256("slash".getBytes());
//                    JWTVerifier verifier = JWT.require(algorithm).build();
//                    DecodedJWT decodedJWT = verifier.verify(token);
//                    String email = decodedJWT.getSubject();
//                    String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
//                           Collection<SimpleGrantedAuthority> authorities =     new ArrayList<>();
//                    stream(roles).forEach(role -> {
//                        authorities.add(new SimpleGrantedAuthority(role));
//
//                    });
//                    UsernamePasswordAuthenticationToken authenticationToken =
//                            new UsernamePasswordAuthenticationToken(email, null, authorities);
//                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    SecurityUti.authorizationUtiJWT(authorizationHeader);
                    filterChain.doFilter(request, response);

                } catch (Exception e) {
//                    log.error("error logging in : {}", e.getMessage());
//                    response.setHeader("error", e.getMessage());
//                    response.setStatus(FORBIDDEN.value());
//                    Map<String, String> error = new HashMap<>();
//                    error.put("error message", e.getMessage());
//                    response.setContentType(APPLICATION_JSON_VALUE);


//                    new ObjectMapper().writeValue(response.getOutputStream(), error);
                    SecurityUti.except(e, response);
                }


            } else {
                filterChain.doFilter(request, response);
            }
        }
    }
}
