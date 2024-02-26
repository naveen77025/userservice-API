package com.example.userservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@org.springframework.context.annotation.Configuration
public class Configuration {
    @Bean
    public BCryptPasswordEncoder passwordGenerator(){
        return new BCryptPasswordEncoder();
    }
}
