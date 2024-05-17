package com.pilleasychat.project.web.signup;

import com.pilleasychat.project.domain.dto.UserDto;
import com.pilleasychat.project.domain.entity.User;
import com.pilleasychat.project.domain.login.LoginService;
import com.pilleasychat.project.domain.signup.SignupService;
import com.pilleasychat.project.web.SessionConst;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/signup")
public class SignupController {

    private final SignupService signupService;

    @Value("${spring.kakao.client_id}")
    private String kakao_client_id;

    @Value("${spring.kakao.redirect_uri}")
    private String kakao_redirect_uri;

    @Value("${spring.security.oauth2.client.registration.google.client_id}")
    private String google_client_id;
    @GetMapping("")
    public String signupPage(@ModelAttribute("user") UserDto user, Model model) {
        String kakaoLocation = "https://kauth.kakao.com/oauth/authorize?response_type=code&client_id="+kakao_client_id+"&redirect_uri="+kakao_redirect_uri;
        String googleLocation = "https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.profile https://www.googleapis.com/auth/userinfo.email&client_id="
                + google_client_id + "&response_type=code&redirect_uri=http://localhost:8080/login/oauth2/code/google&access_type=offline";
        model.addAttribute("kakaoLocation", kakaoLocation);
        model.addAttribute("googleLocation", googleLocation);
        return "html/signup/signup";
    }

    @PostMapping("")
    public String signup(@ModelAttribute("user") UserDto user) {
        signupService.register(user);
        return "redirect:/";
    }

    @GetMapping("/additional")
    public String additionalPage(Model model, HttpServletRequest request) {

        HttpSession session = request.getSession();
        User user = signupService.findByEmail((String)session.getAttribute("userEmail"));
        UserDto userDto = signupService.entityToDto(user);

        model.addAttribute("user", userDto);
        return "html/signup/additional";
    }

    @PostMapping("/additional")
    public String additional(@ModelAttribute("user") UserDto user) {
        signupService.update(user);
        return "redirect:/";
    }
}
