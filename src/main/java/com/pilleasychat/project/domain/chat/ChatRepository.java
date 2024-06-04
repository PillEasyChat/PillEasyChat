package com.pilleasychat.project.domain.chat;

import com.pilleasychat.project.domain.entity.ChatHistory;
import com.pilleasychat.project.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRepository extends JpaRepository<ChatHistory, Long> {
    List<ChatHistory> findByUser(User user);
}
