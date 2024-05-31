package com.pilleasychat.project.web.chatbot.service;

import com.pilleasychat.project.web.chatbot.dto.ChatRequest;
import com.pilleasychat.project.web.chatbot.model.UserModel;
import dev.langchain4j.chain.ConversationalRetrievalChain;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.SystemMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.data.segment.TextSegment;
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
    //private final ConversationalRetrievalChain conversationalRetrievalChain;
    private final EmbeddingStore<TextSegment> embeddingStore;
    private final EmbeddingModel embeddingModel;

    @Override
    public String getResponse(Long userId, ChatRequest request) {
       return assistant.chat(userId.intValue(), request.getMessage());
    }
    /*
    @Override
    public String chatWithPdf(@RequestBody String text) {
        //log.debug("Answer is - {}", answer);
        if (conversationalRetrievalChain.execute(text).contains("과민증")) {
            return "사용자 분은 과민증이 있기 때문에 특히 주의하셔야 합니다."+conversationalRetrievalChain.execute(text);
        } else {
            return conversationalRetrievalChain.execute(text);
        }
    }
    */
    @Override
    public List<EmbeddingMatch<TextSegment>> search(String query, int maxResults) {
        Embedding queryEmbedding = embeddingModel.embed(query).content();
        return embeddingStore.findRelevant(queryEmbedding, maxResults);
    }

    @Override
    public UserModel getUserModelFromId(int id) {
        return assistant.extractUserFromId(id);
    }

}
