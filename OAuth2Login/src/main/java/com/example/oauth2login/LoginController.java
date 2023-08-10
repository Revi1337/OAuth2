package com.example.oauth2login;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController @RequiredArgsConstructor
public class LoginController {

    @GetMapping("/loginPage")
    public String loginPage() {
        return "loginPage";
    }
}
