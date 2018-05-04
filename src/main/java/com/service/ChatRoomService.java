package com.service;

import com.dao.ChatMessageDAO;
import com.dao.ChatRoomDAO;
import com.model.ChatRoom;
import com.sun.javaws.exceptions.InvalidArgumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatRoomService {

    ChatMessageDAO chatMessageDAO;
    ChatRoomDAO chatRoomDAO;

    @Autowired
    public ChatRoomService(ChatMessageDAO chatDAO, ChatRoomDAO chatRoomDAO){
        this.chatMessageDAO = chatDAO;
        this.chatRoomDAO = chatRoomDAO;
    }


    public ChatRoom createNewRoom(String name){
        ChatRoom cr = new ChatRoom(name);
        chatRoomDAO.save(cr);
        return cr;
    }

    public List<ChatRoom> getAllRooms() { return chatRoomDAO.findAll(); }

    public ChatRoom getRoomById(Long id) {
        ChatRoom cr = chatRoomDAO.findById(id).get();
        return cr;
    }


}
