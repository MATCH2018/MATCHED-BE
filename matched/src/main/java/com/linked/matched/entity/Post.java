package com.linked.matched.entity;

import com.linked.matched.entity.status.BoardStatus;
import com.linked.matched.request.post.PostEditor;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private Integer limitPeople;

    @Enumerated(EnumType.STRING)
    private BoardStatus boardName;

    @Column(nullable = false)
    private boolean reported;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @BatchSize(size = 100)
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Applicant> applicant = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<PostReport> postReports = new ArrayList<>();

    @Builder
    public Post(Long postId, String title, String content, Integer limitPeople, BoardStatus boardName,User user) {
        this.postId = postId;
        this.title = title;
        this.content = content;
        this.createdAt = LocalDateTime.now();
        this.limitPeople = limitPeople;
        this.boardName = boardName;
        this.user=user;
    }

    public void edit(PostEditor postEditor){
        this.title=postEditor.getTitle();
        this.content=postEditor.getContent();
        this.limitPeople=postEditor.getLimitPeople();
        this.boardName=postEditor.getBoardName();
    }

    public PostEditor.PostEditorBuilder toEditor(){
        return PostEditor.builder()
                .title(title)
                .content(content)
                .limitPeople(limitPeople)
                .boardName(boardName);
    }

    public void makeStatusReported() {
        this.reported = true;
    }
}
