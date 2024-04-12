package com.pilleasychat.project.web.signup;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/signup")
public class SignupController {
    @GetMapping("")
    public String signupPage() {
        return "html/signup/signup";
    }

    @PostMapping("")
    public String signup() {
        return "";
    }

    @GetMapping("/additional")
    public String additionalPage() {
        return "html/signup/additional";
    }

    @PostMapping("/additional")
    public String additional() {
        return "";
    }
}
