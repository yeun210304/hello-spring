package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomController {

    @GetMapping("/")
    public String home() {
        return "home";
    }

}


