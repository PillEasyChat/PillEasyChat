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

    @Value("${spring.kakao.client_id}")
    private String kakao_client_id;

    @Value("${spring.kakao.redirect_uri}")
    private String kakao_redirect_uri;

    @Value("${spring.security.oauth2.client.registration.google.client_id}")
    private String google_client_id;
    @GetMapping("")
    public String loginPage(LoginForm loginForm, Model model) {
        String kakaoLocation = "https://kauth.kakao.com/oauth/authorize?response_type=code&client_id="+kakao_client_id+"&redirect_uri="+kakao_redirect_uri;
        String googleLocation = "https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.profile https://www.googleapis.com/auth/userinfo.email&client_id="
                + google_client_id + "&response_type=code&redirect_uri=http://localhost:8080/login/oauth2/code/google&access_type=offline";
        model.addAttribute("kakaoLocation", kakaoLocation);
        model.addAttribute("googleLocation", googleLocation);
        return "html/login/login";
    }

    @PostMapping("")
    public String login(@ModelAttribute LoginForm loginForm, HttpServletRequest request) {
        User user = loginService.login(loginForm.getEmail(), loginForm.getPassword());

        if (user == null) {
            return "redirect:/login";
        }
        // 세션을 생성하기 전에 기존의 세션 파기
        request.getSession().invalidate();
        HttpSession session = request.getSession(true);  // Session이 없으면 생성

        session.setAttribute("userEmail", user.getEmail());
        session.setAttribute("userName", user.getNickname());
        session.setAttribute(SessionConst.LOGIN_MEMBER, user);
        session.setMaxInactiveInterval(1800);
        return "redirect:/";
    }

    @GetMapping("/findId")
    public String findId(){
        return "html/login/findidpage";
    }

    @GetMapping("/findPassword")
    public String findPassword(){
        return "html/login/findpasswordpage";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }
}
