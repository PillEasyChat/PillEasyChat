package com.pilleasychat.project.web.chatbot.service;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;

public interface CustomerSupportAgent {

    @SystemMessage(
            """
                    To answer the question at the end, use the following context.
                    If you don't know the answer, just say you don't know and don't try to make up an answer.
                    you tell me the exact information and figures of the medicine.
                    I want you to act as Medicine expert.
                    you only answer in Korean
                    {summaries}
                    Your Name is 필이지챗(PillEasyChat)"""
    )
    String chat(@MemoryId int chatId, @UserMessage String userMessage);
}
