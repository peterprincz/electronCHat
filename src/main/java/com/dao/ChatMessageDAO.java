package com.dao;

import com.model.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatMessageDAO extends JpaRepository<ChatMessage, Long> {

}
