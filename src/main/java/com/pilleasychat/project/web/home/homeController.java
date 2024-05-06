package com.pilleasychat.project.web.home;

import com.pilleasychat.project.domain.dto.UserDto;
import com.pilleasychat.project.domain.entity.User;
import com.pilleasychat.project.domain.signup.SignupService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/home")
public class homeController {

//    SignupService signupService;
    @GetMapping("")
    public String home(Model model, HttpServletRequest request) {

//        HttpSession session = request.getSession();
//        session.setAttribute("userEmail", "Sdsd");
//        User user = signupService.findByEmail((String)session.getAttribute("userEmail"));
//        UserDto userDto = signupService.entityToDto(user);
//
//        model.addAttribute("user", userDto);

        return "html/home/home";
    }
}
