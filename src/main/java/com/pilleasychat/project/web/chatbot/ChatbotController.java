package com.pilleasychat.project.web.chatbot;

import com.pilleasychat.project.domain.chat.ChatService;
import com.pilleasychat.project.domain.entity.ChatHistory;
import com.pilleasychat.project.domain.entity.User;
import com.pilleasychat.project.domain.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ChatbotController {

    private final ChatService chatService;
    private final UserService userService;
    @GetMapping("/chat")
    public String chat(Model model, HttpServletRequest request) {

        HttpSession session = request.getSession();
        User user = userService.findByEmail((String)session.getAttribute("userEmail"));

        List<ChatHistory> list = chatService.findByUser(user);
        model.addAttribute("chatHistory", list);

        return "html/chatbot/chatbot";
    }
}
