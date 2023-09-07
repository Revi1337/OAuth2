package com.example.oauth2login;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.oidc.IdTokenClaimNames;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.HashMap;

@RestController @RequiredArgsConstructor
public class indexController{

    private final ClientRegistrationRepository clientRegistrationRepository;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/user")
    public OAuth2User user(String accessToken) {
        ClientRegistration keycloakRegistration = clientRegistrationRepository.findByRegistrationId("keycloak");

        OAuth2UserRequest oAuth2UserRequest = new OAuth2UserRequest(
                keycloakRegistration,
                new OAuth2AccessToken(OAuth2AccessToken.TokenType.BEARER, accessToken, Instant.now(), Instant.MAX)
        );

        DefaultOAuth2UserService oAuth2UserService = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = oAuth2UserService.loadUser(oAuth2UserRequest);
        return oAuth2User;
    }

    @GetMapping("/oidc")
    public OAuth2User user(String accessToken, String idToken) {
        ClientRegistration keycloakRegistration = clientRegistrationRepository.findByRegistrationId("keycloak");

        OAuth2AccessToken oAuth2AccessToken = new OAuth2AccessToken(
                OAuth2AccessToken.TokenType.BEARER, accessToken, Instant.now(), Instant.MAX);

        HashMap<String, Object> tokenClaims = new HashMap<>();
        tokenClaims.put(IdTokenClaimNames.ISS, "http://localhost:8080/realms/oauth2");
        tokenClaims.put(IdTokenClaimNames.SUB, "OIDC0");
        tokenClaims.put("preferred_username", "user");

        OidcUserRequest oidcUserRequest = new OidcUserRequest(
                keycloakRegistration,
                oAuth2AccessToken,
                new OidcIdToken(idToken, Instant.now(), Instant.MAX, tokenClaims)
        );

        OidcUserService oidcUserService = new OidcUserService();
        OAuth2User oAuth2User = oidcUserService.loadUser(oidcUserRequest);
        return oAuth2User;
    }

}
