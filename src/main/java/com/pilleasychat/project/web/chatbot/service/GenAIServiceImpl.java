package com.pilleasychat.project.web.chatbot.service;

import com.pilleasychat.project.web.chatbot.dto.ChatRequest;
import com.pilleasychat.project.web.chatbot.model.UserModel;
import dev.langchain4j.chain.ConversationalRetrievalChain;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.SystemMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.openai.OpenAiChatModelName;
import dev.langchain4j.service.TokenStream;
import dev.langchain4j.store.embedding.EmbeddingMatch;
import dev.langchain4j.store.embedding.EmbeddingStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GenAIServiceImpl implements GenAIService{

    private final Assistant assistant;
    private final EmbeddingStore<TextSegment> embeddingStore;
    private final EmbeddingModel embeddingModel;
    private final ChatMemory getChatMemory;

    @Override
    public String getResponse(Long userId, ChatRequest request) {
       return assistant.chat(userId.intValue(), request.getMessage());
    }

    @Override
    public String getHistory(Long userId) {
        List<ChatMessage> chatMessages = getChatMemory.messages();
        System.out.print(chatMessages);
        StringBuilder sb = new StringBuilder();

        for (ChatMessage chatMessage : chatMessages) {
            if (chatMessage instanceof AiMessage) {
                AiMessage aiMessage = (AiMessage) chatMessage;
                int index = aiMessage.text().indexOf("Answer using the following information:");
                if (index == -1) {
                    String s = aiMessage.text();
                    if ((sb.toString()).contains(s)) {
                        continue;
                    }
                    sb.append("PillEasyChat: ").append(s).append("\n");
                }
                else {
                    String s = aiMessage.text().substring(0, index).trim();
                    if ((sb.toString()).contains(s)) {
                        continue;
                    }
                    sb.append("PillEasyChat: ").append(s).append("\n");
                }
            }
            else if (chatMessage instanceof UserMessage) {
                UserMessage userMessage = (UserMessage) chatMessage;
                int index = userMessage.singleText().indexOf("Answer using the following information:");
                if (index == -1) {
                    String s = userMessage.singleText();
                    if ((sb.toString()).contains(s)) {
                        continue;
                    }
                    sb.append("User: ").append(s).append("\n");
                }
                else {
                    String s = userMessage.singleText().substring(0, index).trim();
                    if ((sb.toString()).contains(s)) {
                        continue;
                    }
                    sb.append("User: ").append(s).append("\n");
                }
            }
        }
        return sb.toString();
    }
}
