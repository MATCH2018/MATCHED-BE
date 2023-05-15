package com.linked.matched.entity;

import com.linked.matched.entity.status.ReadStatus;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Chat {
    @Id
    @GeneratedValue
    private Long messageId;
    private String message;
    @Enumerated(EnumType.STRING)
    private ReadStatus readCheck;// READ, NOT 형태
    private Date createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="room_id")
    private Room room;

    @Builder
    public Chat(Long messageId, String message, ReadStatus readCheck, Date createdAt) {
        this.messageId = messageId;
        this.message = message;
        this.readCheck = readCheck;
        this.createdAt = createdAt;
    }
}
