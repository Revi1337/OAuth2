package com.example.oauth2login;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController @RequiredArgsConstructor
public class indexController{

    @GetMapping("/")
    public String index() {
        return "index";
    }
}
