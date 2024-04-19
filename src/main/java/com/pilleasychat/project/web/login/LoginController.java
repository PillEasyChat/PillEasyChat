package com.pilleasychat.project.web.login;

import com.pilleasychat.project.domain.entity.User;
import com.pilleasychat.project.domain.login.LoginService;
import com.pilleasychat.project.web.SessionConst;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequiredArgsConstructor
@RequestMapping("/login")
public class LoginController {

    private final LoginService loginService;

    @Value("${kakao.client_id}")
    private String client_id;

    @Value("${kakao.redirect_uri}")
    private String redirect_uri;
    @GetMapping("")
    public String loginPage(LoginForm loginForm, Model model) {
        String location = "https://kauth.kakao.com/oauth/authorize?response_type=code&client_id="+client_id+"&redirect_uri="+redirect_uri;
        model.addAttribute("location", location);
        return "html/login/login";
    }

    @PostMapping("")
    public String login(@ModelAttribute LoginForm loginForm, HttpServletRequest request) {
        User user = loginService.login(loginForm.getEmail(), loginForm.getPassword());

        if (user == null) {
            return "redirect:/login";
        }
        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_MEMBER, user);
        return "redirect:/";
    }

}
