package com.linked.matched.entity;

import com.linked.matched.request.notice.NoticeEditor;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Notice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    public NoticeEditor.NoticeEditorBuilder toEditor(){
        return NoticeEditor.builder()
                .title(title)
                .content(content);
    }

    public void edit(NoticeEditor noticeEditor) {
        this.title = noticeEditor.getTitle();
        this.content=noticeEditor.getContent();
    }
}
