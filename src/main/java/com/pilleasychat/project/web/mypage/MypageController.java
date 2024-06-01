package com.pilleasychat.project.web.mypage;

import com.pilleasychat.project.domain.entity.User;
import com.pilleasychat.project.domain.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/mypage")
@RequiredArgsConstructor
public class MypageController {

    private final UserService userService;
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
        User user = userService.findByEmail((String)session.getAttribute("userEmail"));
        model.addAttribute(user);
        return "html/mypage/update";
    }

    @PostMapping("/update")
    public String update() {
        return "";
    }
}
