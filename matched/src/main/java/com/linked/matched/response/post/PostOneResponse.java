package com.linked.matched.response.post;

import com.linked.matched.entity.status.BoardStatus;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostOneResponse {
    private final String title;
    private final String content;
    private final String createdAt;
    private final Integer limitPeople;
    private final BoardStatus boardName;
    private final Long userId;
    private final String name;
    private final String department;

    public PostOneResponse(String title, String content, LocalDateTime createdAt, Integer limitPeople, BoardStatus boardName, Long userId, String name, String department) {
        this.title = title;
        this.content = content;
        this.createdAt = String.valueOf(createdAt);
        this.limitPeople = limitPeople;
        this.boardName = boardName;
        this.userId = userId;
        this.name = name;
        this.department = department;
    }
}
