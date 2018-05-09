package com.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.model.ChatRoom;
import com.service.ChatRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.websocket.EncodeException;
import javax.websocket.OnMessage;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;


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


    @PostMapping(value = "/join-room")
    public ResponseEntity joinRoom(@RequestBody String body){
        ObjectMapper mapper = new ObjectMapper();
        Map<String,Object> map = new HashMap<String, Object>();
        try {
            map = mapper.readValue(body.toString(), Map.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String userName = (String) map.get("userName");
        Integer roomId = (Integer) map.get("roomId");

        ChatRoom chatRoom = chatRoomService.addUserToRoom(userName, roomId.longValue());
        return ResponseEntity.ok(chatRoom);
    }

    @PostMapping(value = "/leave-room")
    public ResponseEntity leaveRoom(@RequestBody String body){
        ObjectMapper mapper = new ObjectMapper();
        Map<String,Object> map = new HashMap<String, Object>();
        try {
            map = mapper.readValue(body.toString(), Map.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String userName = (String) map.get("userName");
        Integer roomId = (Integer) map.get("roomId");

        ChatRoom chatRoom = chatRoomService.removeUserFromRoom(userName, roomId.longValue());
        return ResponseEntity.ok(chatRoom);
    }

}
