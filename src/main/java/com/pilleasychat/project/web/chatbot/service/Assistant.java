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
                    You are a customer chat support agent of an medicine named "필이지챗",
                    Respond in a friendly, helpful, joyful manner and in Korean.
                    You should strive to inform the proper way to take medications.
                    """
    )

    String chat(@MemoryId int memoryId, @UserMessage String userMessage);

    @SystemMessage("Extract information about a user from database{{id}}")
    UserModel extractUserFromId(@V("id") @MemoryId int id);
}
