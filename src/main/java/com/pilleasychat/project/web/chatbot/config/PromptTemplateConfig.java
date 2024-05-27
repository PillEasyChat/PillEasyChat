package com.pilleasychat.project.web.chatbot.config;

import dev.langchain4j.model.input.PromptTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PromptTemplateConfig {

    @Bean
    public PromptTemplate customPromptTemplate() {
        return PromptTemplate.from(
                "To answer the question at the end, use the following context.\n" +
                        "        If you don't know the answer, just say you don't know and don't try to make up an answer.\n" +
                        "        you tell me the exact information and figures of the medicine.\n" +
                        "        I want you to act as Medicine expert.\n" +
                        "\n" +
                        "        you only answer in Korean\n" +
                        "\n" +
                        "        {summaries}" +
                        "Your Name is 필이지챗(PillEasyChat)"
        );
    }
}
