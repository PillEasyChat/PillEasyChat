package com.pilleasychat.project.web.chatbot.controller;

import com.pilleasychat.project.domain.entity.User;
import com.pilleasychat.project.domain.signup.SignupService;
import com.pilleasychat.project.web.chatbot.dto.ChatRequest;
import com.pilleasychat.project.web.chatbot.dto.ChatResponse;
import com.pilleasychat.project.web.chatbot.model.UserModel;
import com.pilleasychat.project.web.chatbot.service.GenAIService;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.store.embedding.EmbeddingMatch;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chat")
public class GenerativeController {

    private final GenAIService genAIService;
    private final SignupService signupService;

    @PostMapping
    public ChatResponse getChatResponse(@RequestBody ChatRequest chatRequest, HttpServletRequest request) {

        HttpSession session = request.getSession();
        User user = signupService.findByEmail((String)session.getAttribute("userEmail"));

        if (genAIService.getResponse(user.getId(), chatRequest).contains(user.getAllergy())) {
            return new ChatResponse(user.getName()+"님은 "+user.getAllergy()+"증상이 있으므로 특히 조심하세요!"+genAIService.getResponse(user.getId(), chatRequest));
        }
        else {
            return new ChatResponse(genAIService.getResponse(user.getId(), chatRequest));
        }
    }

    @PostMapping("/history")
    public String getHistory(HttpServletRequest request) {
        //HttpSession session = request.getSession();
        //User user = signupService.findByEmail((String)session.getAttribute("userEmail"));
        //user.getId()
        return genAIService.getHistory(3L);
    }
}
