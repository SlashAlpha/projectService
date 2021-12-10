package com.example.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LocalHostRouteConfig {


    @Bean
    public RouteLocator localHostRouter(RouteLocatorBuilder builder){
            return builder.routes()
                    .route(r -> r.path("/api/game/login")
                            .uri("http://localhost:8081/api/login"))
                    .route(r -> r.path("/api/v1/auth/newuser")
                            .uri("http://localhost:8081"))

                    .build();

    }
}
