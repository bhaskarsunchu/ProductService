package com.scaler.ecommerceprojectjune24.configuration;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ApplicationConfiguration {

    @Bean
    //@LoadBalanced
    // This Method Creates & returns the object of RestTemplate class
    // Object will be registered as Spring Bean by This annotation
    public RestTemplate createRestTemplate(){
        return new RestTemplate();
    }
}
