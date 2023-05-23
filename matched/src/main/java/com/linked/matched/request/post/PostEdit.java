package com.linked.matched.request.post;

import com.linked.matched.entity.status.BoardStatus;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostEdit {
    private String title;
    private String content;
    private Integer limitPeople;
    private BoardStatus boardName;

    @Builder
    public PostEdit(String title, String content, Integer limitPeople, BoardStatus boardName) {
        this.title = title;
        this.content = content;
        this.limitPeople = limitPeople;
        this.boardName = boardName;
    }
}
