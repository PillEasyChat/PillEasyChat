package com.pilleasychat.project.web.login;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/login")
@RequiredArgsConstructor
public class GoogleLoginController {

    @GetMapping("/oauth2/code/google/{provider}")
    public String callback(@RequestParam String code){

        return "redirect:/";
    }
}
