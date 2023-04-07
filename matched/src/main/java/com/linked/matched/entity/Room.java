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
    private Date createdAt;
    private Integer personnel;

    @Builder
    public Room(Long roomId, Date createdAt, Integer personnel) {
        this.roomId = roomId;
        this.createdAt = createdAt;
        this.personnel = personnel;
    }
}
