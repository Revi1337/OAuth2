package io.security.cors1;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CorsController {

    @GetMapping(value = "/")
    public String index() {
        return "index";
    }
}
