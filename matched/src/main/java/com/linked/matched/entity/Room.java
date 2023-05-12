package com.linked.matched.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

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


    @Builder
    public Room(Long roomId, String name, Long headUserId, Long tailUserId) {
        this.roomId = roomId;
        this.name = name;
        this.headUserId = headUserId;
        this.tailUserId = tailUserId;
    }
}
