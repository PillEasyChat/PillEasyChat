package com.pilleasychat.project.web.chatbot.config;

import com.pilleasychat.project.web.chatbot.service.Assistant;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.chat.StreamingChatLanguageModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.openai.OpenAiChatModelName;
import dev.langchain4j.model.openai.OpenAiStreamingChatModel;
import dev.langchain4j.model.openai.OpenAiTokenizer;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.store.embedding.EmbeddingStore;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(LangChain4jConfig.class)
@RequiredArgsConstructor
public class AIConfig {

    private final ContentRetriever contentRetriever;

    @Bean
    public Assistant assistant(
    ) {
         return AiServices.builder(Assistant.class)
                 .chatLanguageModel(chatLanguageModel())
                 .chatMemoryProvider(memoryId -> MessageWindowChatMemory.withMaxMessages(10))
                 .contentRetriever(contentRetriever)
                 .build();
    }

    @Bean
    public ChatLanguageModel chatLanguageModel() {
        return OpenAiChatModel.builder()
                .apiKey("sk-proj-1RBVLwxKzQ5MkmN5q25ST3BlbkFJ9q8QegyE91AIwM3yIduv")
                .modelName("ft:gpt-3.5-turbo-0125:personal:thirdtest:9TP0DJuv")
                .build();
    }


}
