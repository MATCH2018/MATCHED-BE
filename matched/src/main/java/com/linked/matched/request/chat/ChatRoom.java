package com.linked.matched.request.chat;


import lombok.Getter;

@Getter
public class ChatRoom {
    private Long roomId;
    private String name;
    private Long headUserId;
    private Long tailUserId;

    public ChatRoom(Long roomId, String name, Long headUserId, Long tailUserId) {
        this.roomId = roomId;
        this.name = name;
        this.headUserId = headUserId;
        this.tailUserId = tailUserId;
    }
}
