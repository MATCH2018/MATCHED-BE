package com.linked.matched.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Room {
    @Id
    @GeneratedValue
    private Long roomId;
    private String name;
    private Long headUserId;
    private Long tailUserId;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private List<Chat> chats=new ArrayList<>();


    @Builder
    public Room(Long roomId, String name, Long headUserId, Long tailUserId) {
        this.roomId = roomId;
        this.name = name;
        this.headUserId = headUserId;
        this.tailUserId = tailUserId;
    }
}
