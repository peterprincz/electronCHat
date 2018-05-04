package com.controller;

import com.model.ChatRoom;
import com.service.ChatRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin
@Controller
public class ChatRoomController {

    ChatRoomService chatRoomService;

    @Autowired
    public ChatRoomController(ChatRoomService chatRoomService){
        this.chatRoomService = chatRoomService;
    }


    @PostMapping("/create-new-room")
    public ResponseEntity createNewRoom(@RequestParam String chatRoomName){
        ChatRoom cr = chatRoomService.createNewRoom(chatRoomName);
        return ResponseEntity.ok(cr);
    }

    @GetMapping("get-all-room")
    public ResponseEntity getAllRoom(){
        List<ChatRoom> chatRooms = chatRoomService.getAllRooms();
        return ResponseEntity.ok(chatRooms);
    }

    @GetMapping("get-room-by-id/{roomId}")
    public ResponseEntity getAllRoom(@PathVariable(value="roomId") Long roomId){
        ChatRoom chatRoom = chatRoomService.getRoomById(roomId);
        return ResponseEntity.ok(chatRoom);
    }

}
