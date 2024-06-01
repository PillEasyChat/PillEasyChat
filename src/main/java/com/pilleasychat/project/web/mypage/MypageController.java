package com.pilleasychat.project.web.mypage;

import com.pilleasychat.project.domain.dto.UserDto;
import com.pilleasychat.project.domain.entity.User;
import com.pilleasychat.project.domain.signup.SignupService;
import com.pilleasychat.project.domain.user.UserService;
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
@RequestMapping("/mypage")
@RequiredArgsConstructor
public class MypageController {

    private final UserService userService;
    private final SignupService signupService;
    @GetMapping("")
    public String mypage(Model model,  HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = userService.findByEmail((String)session.getAttribute("userEmail"));
        model.addAttribute(user);
        return "html/mypage/mypage";
    }

    @GetMapping("/update")
    public String updatePage(Model model,HttpServletRequest request) {
        HttpSession session = request.getSession();
        UserDto user = signupService.entityToDto(userService.findByEmail((String)session.getAttribute("userEmail")));
        model.addAttribute(user);
        return "html/mypage/update";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute UserDto userDto, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = signupService.findByEmail((String)session.getAttribute("userEmail"));
        signupService.update(user, userDto);
        return "redirect:/mypage";
    }
}
