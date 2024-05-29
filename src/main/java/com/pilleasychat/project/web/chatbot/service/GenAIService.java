package com.pilleasychat.project.web.chatbot.service;

import com.pilleasychat.project.web.chatbot.dto.ChatRequest;
import com.pilleasychat.project.web.chatbot.model.UserModel;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.service.TokenStream;
import dev.langchain4j.store.embedding.EmbeddingMatch;
import dev.langchain4j.store.embedding.EmbeddingStore;
import org.antlr.v4.runtime.Token;

import java.io.IOException;
import java.util.List;

public interface GenAIService {

    String getResponse(ChatRequest request);

    //String chatWithPdf(String text);

    List<EmbeddingMatch<TextSegment>> search(String query, int maxResults);
    //TokenStream lcGetResponse(int memoryId, String userMessage);

    UserModel getUserModelFromId(int id);
}
