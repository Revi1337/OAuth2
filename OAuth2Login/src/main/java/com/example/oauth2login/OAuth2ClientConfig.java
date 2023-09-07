package com.example.oauth2login;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.client.oidc.web.logout.OidcClientInitiatedLogoutSuccessHandler;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;


@RequiredArgsConstructor
@Configuration @EnableWebSecurity
public class OAuth2ClientConfig {

    private final ClientRegistrationRepository clientRegistrationRepository;

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
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
//        return httpSecurity
//                .authorizeHttpRequests(request -> request.anyRequest().authenticated())
//                .oauth2Login(Customizer.withDefaults())
//                .build();
//    }

    // OAuth2 2.0 User 모델 소개 (1), (2), UserInfo 엔드포인트 요청하기
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
//        return httpSecurity
//                .authorizeHttpRequests(request -> request.anyRequest().permitAll())
//                .oauth2Login(Customizer.withDefaults())
//                .build();
//    }

    // Spring MVC 에서 인증 객체 참조
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
//        return httpSecurity
//                .authorizeHttpRequests(request -> request.anyRequest().permitAll())
//                .oauth2Login(Customizer.withDefaults())
//                .logout(logout -> logout
//                        .logoutSuccessHandler(oidcLogoutSuccessHandler())
//                        .invalidateHttpSession(true)
//                        .clearAuthentication(true)
//                        .deleteCookies("JSESSIONID"))
//                .build();
//    }
//
//    public LogoutSuccessHandler oidcLogoutSuccessHandler() {
//        OidcClientInitiatedLogoutSuccessHandler oidcClientInitiatedLogoutSuccessHandler =
//                new OidcClientInitiatedLogoutSuccessHandler(clientRegistrationRepository);
//        oidcClientInitiatedLogoutSuccessHandler.setPostLogoutRedirectUri("http://localhost:8081/login");
//        return oidcClientInitiatedLogoutSuccessHandler;
//    }

    // API 커스텀 - Authorziation BaseURI & Redirection BaseURI
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        // authorizationEndpoint() -->
        // OAuth2AuthorizationRequestRedirectFilter 의 DEFAULT_AUTHORIZATION_REQUEST_BASE_URI 를 바꿔준다보면 됨.
        // 해당 값은 로그인페이지에서 요청을 보내는 링크와 같아야함. <a href="/oauth2/v1/authorization">
        // 디폴트는 /oauth2/authorization/{registrationId}

        // redirectionEndpoint() -->
        // OAuth2LoginAuthenticationFilter 의 DEFAULT_FILTER_PROCESSES_URI 를 바꿔준다보면 됨. 주의할 것은 application.yml 과, 인가서버의 redirect_uri 도 수정해주어야 함.
        // ClientRegistration 은 application.yml 을 읽어와 그 정보를 저장하고 인가서버와 통신하기때문에, 일치하지 않으면 통신오류가 생김.
        // 디폴트는 "/login/oauth2/code/*"
        // 조심해야 할 것은 .loginProcessingUrl() 도 .redirectionEndpoint() 와 같은 기능을 하고 대체 가능함. 하지만 둘다 같이 명시하면 .redirectionEndpoint() 가 우선순위가 더 높다.
        return httpSecurity
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/loogin").permitAll()
                        .anyRequest().authenticated())
                .oauth2Login(auth2 -> auth2
                        .loginPage("/loogin")
                        .authorizationEndpoint(authorizationEndpointConfig -> authorizationEndpointConfig
                                .baseUri("/oauth2/v1/authorization"))
                        .redirectionEndpoint(redirectionEndpointConfig -> redirectionEndpointConfig
                                .baseUri("/login/v1/oauth2/code/*")))
                .build();
    }

}
