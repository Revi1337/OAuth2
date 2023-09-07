package com.example.oauth2login;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
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

import java.security.Principal;
import java.time.Instant;
import java.util.HashMap;

@Slf4j
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

    @GetMapping("/authentication")
    public OAuth2User authentication(Authentication authentication) {
        // 인증 객체를 참조하는 방법 1
        OAuth2AuthenticationToken oAuth2AuthenticationToken1 =
                (OAuth2AuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        OAuth2User principal1 = oAuth2AuthenticationToken1.getPrincipal();

        // 인증 객체를 참조하는 방법 2
        OAuth2AuthenticationToken oAuth2AuthenticationToken2 = (OAuth2AuthenticationToken) authentication;
        OAuth2User principal2 = oAuth2AuthenticationToken2.getPrincipal();

        return principal1;
    }

    // TODO: @AuthenticationPrincipal 는 AuthenticationPrincipalArgumentResolver 클래스에서 요청을 가로채어 바인딩 처리를 한다.
    //  Authentication 를 SecurityContext 로 부터 꺼내와서 Principal 속성에 OAuth2User 혹은 OidcUser 타입의 객체를 저장함.

    @GetMapping("/oauth2-authentication")
    public OAuth2User oAuth2User(@AuthenticationPrincipal OAuth2User oAuth2User) {
        // TODO OIDC 인증 유저, 일반 OAuth2 인증 유저 둘다 여기로 들어올 수 있음.
        // TODO: 그이유는 OidcUser 는 OAuth2User 를 상속하고 있기때문.
        log.info("oAuth2User = {}", oAuth2User);
        return oAuth2User;
    }

    @GetMapping("/oidc-authentication")
    public OidcUser oidcUser(@AuthenticationPrincipal OidcUser oidcUser) {
        // TODO OIDC 만 여기로 들어올 수 있음.
        // TODO 일반 OAuth2 인증 유저가 들어오면 타입이 맞지 않음.
        log.info("oidcUser = {}", oidcUser);
        return oidcUser;
    }


}
