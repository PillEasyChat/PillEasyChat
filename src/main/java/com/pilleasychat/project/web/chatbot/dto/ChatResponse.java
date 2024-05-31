package com.pilleasychat.project.web.chatbot.dto;

public class ChatResponse{
    private String response;

    // Constructor
    public ChatResponse(String response) {
        this.response = response;
    }

    // Getter and Setter
    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
