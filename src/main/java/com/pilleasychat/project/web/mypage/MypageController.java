package com.pilleasychat.project.web.mypage;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/mypage")
public class MypageController {
    @GetMapping("")
    public String mypage() {
        return "html/mypage/mypage";
    }

    @GetMapping("/update")
    public String updatePage() {
        return "html/mypage/update";
    }

    @PostMapping("/update")
    public String update() {
        return "";
    }
}
