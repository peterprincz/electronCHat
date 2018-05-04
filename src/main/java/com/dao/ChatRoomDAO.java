package com.dao;

import com.model.ChatMessage;
import com.model.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomDAO extends JpaRepository<ChatRoom, Long> {
}
