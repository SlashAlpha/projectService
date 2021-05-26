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
                    .route(r->r.path("/api/v1/dealer/pokerCard")
                    .uri("http://localhost:8080"))
                    .route(r->r.path("/api/v1/dealer/blackJackCard")
                    .uri("http://localhost:8080"))

                    .build();

    }
}
