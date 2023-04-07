package com.linked.matched.response.post;

import com.linked.matched.entity.Post;
import com.linked.matched.entity.status.BoardStatus;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostResponse {

    private final Long postId;
    private final String title;
    private final String content;
    private final LocalDateTime createdAt;
    private final LocalDateTime updateAt;
    private final Integer limitPeople;
    private final BoardStatus boardName;

    public PostResponse(Post post) {
        this.postId = post.getPostId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.createdAt = post.getCreatedAt();
        this.updateAt = post.getUpdateAt();
        this.limitPeople = post.getLimitPeople();
        this.boardName = post.getBoardName();
    }
}
