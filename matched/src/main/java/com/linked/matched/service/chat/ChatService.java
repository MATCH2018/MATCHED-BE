package com.linked.matched.service.chat;

import com.linked.matched.request.chat.ChatMessage;
import com.linked.matched.request.chat.ChatRoom;
import com.linked.matched.response.chat.RoomResponse;

import java.util.List;

public interface ChatService {
    List<RoomResponse> findAllChatRoom(Long userId);

    RoomResponse findRoom(Long roomId);

    void createChatRoom(ChatRoom chatRoom);
}
