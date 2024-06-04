package com.pilleasychat.project.domain.chat;

import com.pilleasychat.project.domain.entity.ChatHistory;
import com.pilleasychat.project.domain.entity.User;

import java.util.List;

public interface ChatService {
    void save(String content, User user, String isUser);

    List<ChatHistory> findByUser(User user);
}
