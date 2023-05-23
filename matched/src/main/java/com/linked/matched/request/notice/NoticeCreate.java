package com.linked.matched.request.notice;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NoticeCreate {
    private String title;
    private String content;
    private LocalDate createdAt;
    private Long userId;

    @Builder
    public NoticeCreate(String title, String content,Long userId) {
        this.title = title;
        this.content = content;
        this.createdAt = LocalDate.now();
        this.userId=userId;
    }
}
