package com.pilleasychat.project.web.login;

import com.pilleasychat.project.domain.entity.User;
import com.pilleasychat.project.domain.login.KakaoLoginService;
import com.pilleasychat.project.domain.login.LoginService;
import com.pilleasychat.project.domain.signup.SignupService;
import com.pilleasychat.project.domain.user.UserService;
import com.pilleasychat.project.web.SessionConst;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.util.HashMap;

@Controller
@RequestMapping("/login")
@RequiredArgsConstructor
public class KakaoLoginController {

    private final LoginService loginService;
    private final SignupService signupService;
    private final UserService userService;
    private final KakaoLoginService kakaoLoginService;

    @Value("${spring.kakao.client_id}")
    private String client_id;

    @GetMapping("/callback")
    public String callback(@RequestParam("code") String code, HttpServletRequest request) throws IOException {
        String accessToken = kakaoLoginService.getAccessTokenFromKakao(client_id, code);
        User user = kakaoLoginService.getUserInfo(accessToken);

        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_MEMBER, user);

        return "redirect:/";
    }
}
