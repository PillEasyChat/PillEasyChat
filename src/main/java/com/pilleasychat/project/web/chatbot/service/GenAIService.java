package com.pilleasychat.project.web.chatbot.service;

import com.pilleasychat.project.web.chatbot.dto.ChatRequest;
import com.pilleasychat.project.web.chatbot.model.UserModel;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.service.TokenStream;
import dev.langchain4j.store.embedding.EmbeddingMatch;
import dev.langchain4j.store.embedding.EmbeddingStore;
import org.antlr.v4.runtime.Token;

import java.io.IOException;
import java.util.List;

public interface GenAIService {

    String getResponse(Long userId, ChatRequest request);

    String getHistory(Long userId);

}
