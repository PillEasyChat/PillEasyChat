package com.pilleasychat.project.web.signup;

import com.pilleasychat.project.domain.entity.User;
import com.pilleasychat.project.domain.signup.SignupService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/signup")
public class SignupController {

    private final SignupService signupService;
    @GetMapping("")
    public String signupPage() {
        return "html/signup/signup";
    }

    @PostMapping("")
    public String signup(@ModelAttribute("user") User user) {
        signupService.register(user);
        return "redirect:/";
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
