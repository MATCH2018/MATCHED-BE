package com.linked.matched.response.chat;

import com.linked.matched.entity.Room;
import lombok.Getter;

@Getter
public class RoomResponse {

    private Long roomId;
    private String name;
    private Long headUserId;
    private Long tailUserId;

    public RoomResponse(Room room) {
        this.roomId = room.getRoomId();
        this.name = room.getName();
        this.headUserId=room.getHeadUserId();
        this.tailUserId=room.getTailUserId();
    }
}
