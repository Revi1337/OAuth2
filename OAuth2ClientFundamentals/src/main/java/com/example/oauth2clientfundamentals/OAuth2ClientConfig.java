package com.example.oauth2clientfundamentals;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.ClientRegistrations;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;

import java.util.List;



@Configuration
public class OAuth2ClientConfig {

    //TODO ClientRegistrationRepository 이해 및 활용.

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        return new InMemoryClientRegistrationRepository(keycloakClientRegistration());
    }

    // 아래처럼 Java 코드로 설정하려면 yml 파일의 provider, 및 registration 관련 설정을 지워야 함.
    private ClientRegistration keycloakClientRegistration() {
        return ClientRegistrations.fromIssuerLocation("http://localhost:8080/realms/oauth2")
                .registrationId("keycloak")                                         // yaml 혹은 properties 파일에서의 spring.security.oauth2.client.registration.[registrationId] 를 의미함.
                .clientId("VDB-KR")                                                 // 필수값
                .clientSecret("S0IAlcx5hI9WPFuEpgxNzyjxi7Aar6Y9")                   // 필수값
                .redirectUri("http://localhost:8081/login/oauth2/code/keyclock")    // 필수값아니지만 명시 (AuthorizationServer 마다 다름)
                .build();
    }
}
