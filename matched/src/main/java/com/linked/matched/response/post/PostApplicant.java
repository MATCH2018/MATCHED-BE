package com.linked.matched.response.post;

import com.linked.matched.entity.status.BoardStatus;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostApplicant {
    private final Long postId;
    private final String title;
    private final String content;
    private final LocalDateTime createdAt;
    private final Integer limitPeople;
    private final BoardStatus boardName;

    public PostApplicant(Long postId, String title, String content, LocalDateTime createdAt, Integer limitPeople, BoardStatus boardName) {
        this.postId = postId;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.limitPeople = limitPeople;
        this.boardName = boardName;
    }
}
