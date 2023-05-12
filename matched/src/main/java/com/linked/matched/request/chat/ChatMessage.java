package com.linked.matched.request.chat;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessage {

    private Long roomId;
    private Long userId;
    private String writer;
    private String message;

}
