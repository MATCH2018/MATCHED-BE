package com.linked.matched.controller.chat;

import com.linked.matched.request.chat.ChatRoom;
import com.linked.matched.response.chat.RoomResponse;
import com.linked.matched.service.chat.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ChatRoomController {

    private final ChatService chatService;

    @GetMapping("/chat/{userId}")
    public List<RoomResponse> room(@PathVariable Long userId){
        return chatService.findAllChatRoom(userId);
    }

    @GetMapping("/chat/{userId}/{roomId}")
    public RoomResponse findRoom(@PathVariable Long userId,@PathVariable Long roomId){
        return chatService.findRoom(roomId);
    }

    @PostMapping("/chat/{userId}")
    public void createRoom(@PathVariable Long userId, @RequestBody ChatRoom chatRoom){
        chatService.createChatRoom(chatRoom);
    }

}
