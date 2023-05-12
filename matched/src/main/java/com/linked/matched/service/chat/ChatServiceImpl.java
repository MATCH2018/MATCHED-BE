package com.linked.matched.service.chat;

import com.linked.matched.entity.Room;
import com.linked.matched.exception.RoomNotFound;
import com.linked.matched.repository.chat.RoomRepository;
import com.linked.matched.request.chat.ChatMessage;
import com.linked.matched.request.chat.ChatRoom;
import com.linked.matched.response.chat.RoomResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService{

    private final RoomRepository roomRepository;

    //채팅방을 최근 만든 순으로 반환한다.
    @Override
    public List<RoomResponse> findAllChatRoom(Long userId){
        return roomRepository.findAll().stream()
                .map(RoomResponse::new)
                .collect(Collectors.toList());

    }

    //채팅방 찾아서 들어가기
    @Override
    public RoomResponse findRoom(Long roomId){
        return roomRepository.findById(roomId)
                .map(RoomResponse::new)
                .orElseThrow(RoomNotFound::new);

    }

    //채팅방 만들기 - 채팅방 구성해야한다.
    @Override
    @Transactional
    public void createChatRoom(ChatRoom chatRoom){
        Room.builder()
                .name(chatRoom.getName())
                .headUserId(chatRoom.getHeadUserId())
                .tailUserId(chatRoom.getTailUserId())
                .build();
    }
}
