package com.pilleasychat.project.web.chatbot.service;

import com.pilleasychat.project.web.chatbot.model.UserModel;
import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;
// import dev.langchain4j.service.TokenStream;

public interface Assistant {

    @SystemMessage(
            """
                    You are a customer chat support agent of an medicine named "필이지챗(PillEasyChat)".
                    Respond in a friendly, helpful, joyful manner and in Korean.
                    You should strive to inform the proper way to take medications.
                    To answer the question at the end, use the following context.
                    If you don't know the answer, just say you don't know and don't try to make up an answer.
                    you tell me the exact information and figures of the medicine.
                    I want you to act as Medicine expert.
                    {summaries}
                    """
    )

    String chat(@MemoryId int memoryId, @UserMessage String userMessage);

    @SystemMessage("Extract information about a user from database{{id}}")
    UserModel extractUserFromId(@V("id") @MemoryId int id);
}
