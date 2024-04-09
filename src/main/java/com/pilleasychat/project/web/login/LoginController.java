package com.pilleasychat.project.web.login;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class LoginController {
    @GetMapping("")
    public String loginPage() {
        return "html/login/login";
    }

    @PostMapping("")
    public String login() {
        return "/";
    }


}
