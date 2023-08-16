package com.example.oauth2clientfundamentals;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController @RequiredArgsConstructor
public class OAuth2Controller {

    private final ClientRegistrationRepository clientRegistrationRepository;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/clientRegistrationRepository")
    public ClientRegistration clientRegistrationRepository() {
        return clientRegistrationRepository.findByRegistrationId("keycloak");
    }

}
