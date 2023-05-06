package com.linked.matched.request.post;

import com.linked.matched.entity.status.BoardStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
public class PostEdit {
    private String title;
    private String content;
    private LocalDateTime updateAt;
    private Integer limitPeople;
    private BoardStatus boardName;
    //category
    private String categoryName;

    @Builder
    public PostEdit(String title, String content, Integer limitPeople, BoardStatus boardName, String categoryName) {
        this.title = title;
        this.content = content;
        this.updateAt = LocalDateTime.now();
        this.limitPeople = limitPeople;
        this.boardName = boardName;
        this.categoryName = categoryName;
    }
}
