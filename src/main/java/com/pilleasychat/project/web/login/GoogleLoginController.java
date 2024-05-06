package com.pilleasychat.project.web.login;

import com.pilleasychat.project.domain.entity.User;
import com.pilleasychat.project.domain.login.GoogleLoginService;
import com.pilleasychat.project.web.SessionConst;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
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
    public String successGoogleLogin(@RequestParam("code") String accessCode, HttpServletRequest request){
        User user = googleLoginService.getGoogleAccessToken(accessCode);

        // 세션을 생성하기 전에 기존의 세션 파기
        request.getSession().invalidate();
        HttpSession session = request.getSession(true);  // Session이 없으면 생성
        session.setAttribute("userEmail", user.getEmail());
        session.setAttribute(SessionConst.LOGIN_MEMBER, user);
        session.setMaxInactiveInterval(1800);
        return "redirect:/";
    }
}
