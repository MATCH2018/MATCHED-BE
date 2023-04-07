package com.linked.matched.request.post;

import com.linked.matched.entity.status.BoardStatus;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Getter
public class PostCreate {

    private String title;
    private String content;
    private Date createdAt;
    private Integer limitPeople;
    private BoardStatus boardName;
    //category
    private String categoryName;

    @Builder
    public PostCreate(String title, String content, Date createdAt, Integer limitPeople, BoardStatus boardName, String categoryName) {
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.limitPeople = limitPeople;
        this.boardName = boardName;
        this.categoryName = categoryName;
    }
}
