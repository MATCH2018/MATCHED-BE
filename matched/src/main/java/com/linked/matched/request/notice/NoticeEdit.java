package com.linked.matched.request.notice;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NoticeEdit {
    private String title;
    private String content;

    @Builder
    public NoticeEdit(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
