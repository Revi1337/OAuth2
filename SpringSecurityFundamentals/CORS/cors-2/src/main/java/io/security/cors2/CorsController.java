package io.security.cors2;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api")
public class CorsController {

    @GetMapping(value = "/users")
    public User users() {
        return new User("user", 20);
    }
}
