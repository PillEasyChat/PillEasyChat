package com.pilleasychat.project.web.interceptor;

import com.pilleasychat.project.domain.entity.User;
import com.pilleasychat.project.domain.signup.SignupService;
import com.pilleasychat.project.domain.user.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
@RequiredArgsConstructor
public class AdditionalCheckInterceptor implements HandlerInterceptor {

    private final UserRepository userRepository;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();

        HttpSession session = request.getSession();
        User user = userRepository.findByEmail((String)session.getAttribute("userEmail")).orElse(null);
        System.out.println("asdsads" + user);
        if (!userRepository.checkAdditional(user)) {
            response.sendRedirect("/signup/additional?redirectURL="+ requestURI);
            return false;
        }
        return true;
    }
}
