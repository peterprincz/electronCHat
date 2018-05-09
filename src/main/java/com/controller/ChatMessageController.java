package com.controller;

import com.model.ChatRoom;
import com.service.ChatMessageService;
import com.model.ChatMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@CrossOrigin
@Controller
public class ChatMessageController {

    ChatMessageService chatService;


    @Autowired
    public ChatMessageController(ChatMessageService chatService) {
        this.chatService = chatService;
    }

    @MessageMapping("/chatroom/{roomId}/new-message")
    @SendTo("/frontend-listener/chatroom/{roomId}/new-message")
    public ResponseEntity sendMessage(ChatMessage chatMessageFromJS) {
        ChatMessage cm = chatService.saveMessage(chatMessageFromJS);
        return ResponseEntity.ok(cm);
    }



    @GetMapping("/get-all-messages")
    public ResponseEntity getMessages(){
        return ResponseEntity.ok(chatService.getAllMessages());
    }

}