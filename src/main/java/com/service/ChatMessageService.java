package com.service;

import com.dao.ChatMessageDAO;
import com.dao.ChatRoomDAO;
import com.model.ChatMessage;
import com.model.ChatRoom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class ChatMessageService {

    ChatMessageDAO chatMessageDAO;
    ChatRoomDAO chatRoomDAO;

    @Autowired
    public ChatMessageService(ChatMessageDAO chatDAO, ChatRoomDAO chatRoomDAO){
        this.chatMessageDAO = chatDAO;
        this.chatRoomDAO = chatRoomDAO;
    }


    public ChatMessage saveMessage(ChatMessage chatMessageFromJS){
        ChatMessage realChatmessage = new ChatMessage(chatMessageFromJS.getChatRoomId(),
                                                      chatMessageFromJS.getContent(),
                                                      chatMessageFromJS.getSender());
        ChatRoom chatRoom = chatRoomDAO.findById(realChatmessage.getChatRoomId()).get();
        chatRoom.addMessage(realChatmessage);
        chatMessageDAO.save(realChatmessage);
        return realChatmessage;
    }



    public List<ChatMessage> getMessagesByRoomId(Long chatRoomId) {
        ChatRoom cr = chatRoomDAO.findById(chatRoomId).get();
        return cr.getChatMessages();
    }


    public List<ChatMessage> getAllMessages(){
        return chatMessageDAO.findAll();
    }


}
