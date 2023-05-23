package com.linked.matched.request.post;

import com.linked.matched.entity.status.BoardStatus;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostCreate {

    private String title;
    private String content;
    private LocalDate createdAt;
    private Integer limitPeople;
    private BoardStatus boardName;
    private Long userId;

    @Builder
    public PostCreate(String title, String content, Integer limitPeople, BoardStatus boardName,Long userId) {
        this.title = title;
        this.content = content;
        this.createdAt = LocalDate.now();
        this.limitPeople = limitPeople;
        this.boardName = boardName;
        this.userId=userId;
    }
}
