package com.pilleasychat.project.web.chatbot.config;

import com.pilleasychat.project.web.chatbot.service.Assistant;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.chat.StreamingChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.openai.OpenAiChatModelName;
import dev.langchain4j.model.openai.OpenAiStreamingChatModel;
import dev.langchain4j.model.openai.OpenAiTokenizer;
import dev.langchain4j.service.AiServices;
import lombok.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AIConfig {

    @Bean
    public Assistant assistant() {
         return AiServices.builder(Assistant.class)
                 .chatLanguageModel(chatLanguageModel())
                 .chatMemoryProvider(memoryId -> MessageWindowChatMemory.withMaxMessages(10))
                 .build();
    }

    @Bean
    public ChatLanguageModel chatLanguageModel() {
        return OpenAiChatModel.builder()
                .apiKey("sk-proj-OtW1RXHzcGmbRk42LfwBT3BlbkFJN0hoa0s3OiwHXnJ1x7wj")
                .modelName("ft:gpt-3.5-turbo-0125:personal:thirdtest:9TP0DJuv")
                .build();
    }


}
