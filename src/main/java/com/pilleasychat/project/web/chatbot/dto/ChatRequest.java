package com.pilleasychat.project.web.chatbot.dto;

import lombok.Getter;
import lombok.Setter;

public class ChatRequest {
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
