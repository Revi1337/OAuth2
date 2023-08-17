package com.example.oauth2login;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration @EnableWebSecurity
public class OAuth2ClientConfig {

//    // OAuth2LoginConfigurer 초기화 이해
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
//        return httpSecurity
//                .authorizeHttpRequests(request -> request.anyRequest().authenticated())
//                .oauth2Login(Customizer.withDefaults())
//                .build();
//    }


//    // OAuth2 Login Page 생성 (1) - 커스텀
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
//        return httpSecurity
//                .authorizeHttpRequests(request -> request.requestMatchers("/loginPage").permitAll())
//                .oauth2Login(oauth2 -> oauth2.loginPage("/loginPage"))
//                .build();
//    }

//    // OAuth2 Login Page 생성 (2) - 디폴트
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
//        return httpSecurity
//                .authorizeHttpRequests(request -> request.anyRequest().authenticated())
//                .oauth2Login(Customizer.withDefaults())
//                .build();
//    }

    // Authorization Code 요청
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
//        return httpSecurity
//                .authorizeHttpRequests(request -> request.anyRequest().authenticated())
//                .oauth2Login(Customizer.withDefaults())
//                .build();
//    }

    // AccessToken 교환
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .authorizeHttpRequests(request -> request.anyRequest().authenticated())
                .oauth2Login(Customizer.withDefaults())
                .build();
    }


}
