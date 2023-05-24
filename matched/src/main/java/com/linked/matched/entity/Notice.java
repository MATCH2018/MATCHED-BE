package com.linked.matched.entity;

import com.linked.matched.request.notice.NoticeEdit;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Notice {
    @Id
    @GeneratedValue
    private Long noticeId;
    private String title;
    private String content;
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Notice(Long noticeId, String title, String content,User user) {
        this.noticeId = noticeId;
        this.title = title;
        this.content = content;
        this.createdAt = LocalDateTime.now();
        this.user = user;
    }

    public void edit(NoticeEdit noticeEdit) {
        this.title = noticeEdit.getTitle();
        this.content=noticeEdit.getContent();
    }
}
