package com.pilleasychat.project.web.home;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class homeController {

    @GetMapping("")
    public String home() {
        return "html/home/home";
    }
}
