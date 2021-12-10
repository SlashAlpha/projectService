package slash.code.game.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import slash.code.game.config.security.filters.CustomAuthenticationFilter;
import slash.code.game.config.security.filters.CustomAuthorizationFilter;

import static org.springframework.http.HttpMethod.GET;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder passwordEncoder;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues());
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManagerBean());
        customAuthenticationFilter.setFilterProcessesUrl("/api/login");
        http.csrf().disable();

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests().antMatchers(
                "/api/login/**",
                "/api/v1/auth/refreshtoken/**",
                "/api/v1/auth/newuser/**",
                "/v2/api-docs",
                "/configuration/ui",
                "/swagger-resources/**",
                // "/configuration/security",
                "/swagger-ui.html",
                "/webjars/**").permitAll();
        http.authorizeRequests().antMatchers(GET, "/api/v1/auth/**").hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests().anyRequest().authenticated();
        http.addFilter(customAuthenticationFilter);
        http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    }



//        http
//                .authorizeRequests(authorize -> {
//                    authorize
//                            .antMatchers("/", "/webjars/**", "/login", "/resources/**").permitAll()
//                            .antMatchers("/api/v1/poker/newplay").permitAll()
//                            .antMatchers(HttpMethod.GET, "/api/v1/poker/**").permitAll()
//                            .antMatchers("/api/v1/**").permitAll()
//                            .mvcMatchers(HttpMethod.POST,"/api/v1/**").permitAll()
//                            .mvcMatchers(HttpMethod.GET,"/api/v1/**").permitAll();
//
//
//                } )
//                .authorizeRequests()
//                .anyRequest().authenticated()
//                .and()
//                .formLogin().and()
//                .httpBasic();}


    //    @Override
//    @Bean
//    protected UserDetailsService userDetailsService() {
//        UserDetails admin= User.withDefaultPasswordEncoder()
//                .username("slash")
//                .password("slash")
//                .roles("ADMIN")
//                .build();
//        UserDetails user= User.withDefaultPasswordEncoder()
//                .username("user")
//                .password("user")
//                .roles("USER")
//                .build();
//
//        return new InMemoryUserDetailsManager(admin,user);
//    }
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser("slash").password("{noop}slash").roles("ADMIN")
//                .and()
//                .withUser("user").password("{noop}user").roles("USER");
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }


}
