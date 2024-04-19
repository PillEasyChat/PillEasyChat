package com.pilleasychat.project.web.login;

import com.pilleasychat.project.domain.login.GoogleLoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/login")
@RequiredArgsConstructor
public class GoogleLoginController {

    private final GoogleLoginService googleLoginService;

    @GetMapping("/oauth2/code/google")
    public String successGoogleLogin(@RequestParam("code") String accessCode){
        googleLoginService.getGoogleAccessToken(accessCode);
        return "redirect:/";
    }
}
