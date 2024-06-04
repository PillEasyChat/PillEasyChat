package com.pilleasychat.project.domain.chat;

import com.pilleasychat.project.domain.entity.ChatHistory;
import com.pilleasychat.project.domain.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final ChatRepository chatRepository;
    @Override
    public void save(String content, User user, String isUser) {
        ChatHistory chatHistory = ChatHistory.builder()
                .content(content)
                .user(user)
                .date(LocalDateTime.now())
                .isUser(isUser)
                .build();
        chatRepository.save(chatHistory);
    }

    @Override
    public List<ChatHistory> findByUser(User user) {
        return chatRepository.findByUser(user);
    }
}
