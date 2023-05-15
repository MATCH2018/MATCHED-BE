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
    private LocalDate date;

    @Builder
    public NoticeCreate(String title, String content) {
        this.title = title;
        this.content = content;
        this.date = LocalDate.now();
    }
}
