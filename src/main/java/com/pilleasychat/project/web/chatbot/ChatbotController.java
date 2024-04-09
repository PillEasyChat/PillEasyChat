package com.pilleasychat.project.web.chatbot;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ChatbotController {
    @GetMapping("")
    public String chat() {
        return "html/chatbot/chatbot";
    }
}
