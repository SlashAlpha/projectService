package com.example.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;


public class GoogleConfig {

    public RouteLocator routeLocator(RouteLocatorBuilder builder){
        return builder.routes()
                .route(r -> r.path("/googlesearch")

                        .uri("https://www.twitch.tv")).build();
    }
}
