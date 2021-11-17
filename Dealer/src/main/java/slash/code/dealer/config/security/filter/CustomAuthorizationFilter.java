package slash.code.dealer.config.security.filter;

import org.springframework.web.filter.OncePerRequestFilter;
import slash.code.dealer.config.security.SecurityUti;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

public class CustomAuthorizationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getServletPath().equals("/api/login") || request.getServletPath().equals("/refreshtoken")) {
            filterChain.doFilter(request, response);
        } else {
            String authorizationHeader = request.getHeader(AUTHORIZATION);
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                try {
                    SecurityUti.authorizationUtiJWT(authorizationHeader);
                    filterChain.doFilter(request, response);
                } catch (Exception e) {
                    SecurityUti.except(e, response);
                }
            } else {
                filterChain.doFilter(request, response);
            }

        }
    }
}
