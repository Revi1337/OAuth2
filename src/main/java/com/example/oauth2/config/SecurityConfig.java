package com.example.oauth2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        CustomSecurityConfigurer customSecurityConfigurer = new CustomSecurityConfigurer();
        customSecurityConfigurer.setFlag(true);
        return httpSecurity
                .authorizeHttpRequests(request ->
                        request.anyRequest().permitAll())
                .formLogin().and()
                .apply(new CustomSecurityConfigurer().setFlag(false)).and()
                .build();
    }

    @Bean
    public SecurityFilterChain filterChain2(HttpSecurity httpSecurity) throws Exception {
        CustomSecurityConfigurer customSecurityConfigurer = new CustomSecurityConfigurer();
        customSecurityConfigurer.setFlag(true);
        return httpSecurity
                .authorizeHttpRequests(request ->
                        request.anyRequest().permitAll())
                .httpBasic().and()
                .apply(new CustomSecurityConfigurer().setFlag(false)).and()
                .build();
    }

}
