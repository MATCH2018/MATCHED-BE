package com.linked.matched.entity;

import com.linked.matched.entity.status.BoardStatus;
import com.linked.matched.request.post.PostEdit;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Post {
    @Id
    @GeneratedValue
    private Long postId;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updateAt;
    private Integer limitPeople;
    @Enumerated(EnumType.STRING)
    private BoardStatus boardName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Category> categories = new ArrayList<>();


    @Builder
    public Post(Long postId, String title, String content, Integer limitPeople, BoardStatus boardName) {
        this.postId = postId;
        this.title = title;
        this.content = content;
        this.createdAt = LocalDateTime.now();
        this.updateAt = LocalDateTime.now();
        this.limitPeople = limitPeople;
        this.boardName = boardName;
    }

    public void edit(PostEdit postEdit){
        this.title=postEdit.getTitle();
        this.content=postEdit.getContent();
        this.updateAt= LocalDateTime.now();
        this.limitPeople=postEdit.getLimitPeople();
        this.boardName=postEdit.getBoardName();
    }
}
