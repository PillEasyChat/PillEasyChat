package com.pilleasychat.project.web.chatbot;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/chat")
public class ChatbotController {
    @GetMapping("")
    public String home() {
        return "html/chatbot/chatbot";
    }
}
