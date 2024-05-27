package com.pilleasychat.project.web.chatbot.controller;

import com.pilleasychat.project.web.chatbot.dto.ChatRequest;
import com.pilleasychat.project.web.chatbot.dto.ChatResponse;
import com.pilleasychat.project.web.chatbot.model.UserModel;
import com.pilleasychat.project.web.chatbot.service.GenAIService;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.store.embedding.EmbeddingMatch;
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

    @PostMapping
    public ChatResponse getChatResponse(@RequestBody ChatRequest request) {
        return new ChatResponse(genAIService.getResponse(request));
    }

    /*
    @PostMapping("/agent")
    public ChatResponse chat(@RequestBody ChatRequest request) {
        return new ChatResponse(genAIService.chat(request));
    }
    */

    @PostMapping("/pdf")
    public String chatWithPdf(@RequestBody String text) {
        return genAIService.chatWithPdf(text);
    }

    @PostMapping("/pdf2")
    public String chatWithPdf2(@RequestBody String text) {
        List<EmbeddingMatch<TextSegment>> search = genAIService.search(text, 1);
        return search.getFirst().toString();
    }

    @PostMapping("/user")
    public UserModel getUserModelFromId(@RequestBody ChatRequest request) {
        return genAIService.getUserModelFromId(request.userId());
    }
}
