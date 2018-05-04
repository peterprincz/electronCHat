package com.model;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @ElementCollection
    private List<String> members;

    @OneToMany(mappedBy = "chatRoomId", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<ChatMessage> chatMessages;

    public ChatRoom(){

    }

    public ChatRoom(String name) {
        this.name = name;
        this.members = new ArrayList<String>();
        this.chatMessages = new ArrayList<ChatMessage>();
    }

    public void addMessage(ChatMessage chatMessage){
        this.chatMessages.add(chatMessage);
    }

    public void addMember(String member){
        this.members.add(member);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<String> getMembers() {
        return members;
    }

    public void setMembers(List<String> members) {
        this.members = members;
    }

    public List<ChatMessage> getChatMessages() {
        return chatMessages;
    }

    public void setChatMessages(List<ChatMessage> chatMessages) {
        this.chatMessages = chatMessages;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
