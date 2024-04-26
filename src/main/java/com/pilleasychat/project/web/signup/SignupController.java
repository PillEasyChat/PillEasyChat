package com.pilleasychat.project.web.signup;

import com.pilleasychat.project.domain.entity.User;
import com.pilleasychat.project.domain.login.LoginService;
import com.pilleasychat.project.domain.signup.SignupService;
import com.pilleasychat.project.web.SessionConst;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
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
    private final LoginService loginService;
    @GetMapping("")
    public String signupPage(@ModelAttribute("user") User user) {
        return "html/signup/signup";
    }

    @PostMapping("")
    public String signup(@ModelAttribute("user") User user) {
        signupService.register(user);
        return "redirect:/";
    }

    @GetMapping("/additional")
    public String additionalPage(Model model, HttpServletRequest request) {

        HttpSession session = request.getSession();
        User user = signupService.findByEmail((String)session.getAttribute("userEmail"));
        System.out.println("user = " + user);

        model.addAttribute("user", new String[]{
                user.getName(),
                user.getNickname(),
                user.getSpecialNote(),
                user.getAllergy(),
                user.getTakingMedication()});
        return "html/signup/additional";
    }

    @PostMapping("/additional")
    public String additional(@ModelAttribute("user") User user) {
        signupService.register(user);
        return "redirect:/";
    }
}
