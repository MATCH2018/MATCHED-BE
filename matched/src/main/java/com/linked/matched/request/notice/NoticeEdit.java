package com.linked.matched.request.notice;

import lombok.Builder;
import lombok.Getter;

@Getter
public class NoticeEdit {
    private String title;
    private String content;

    @Builder
    public NoticeEdit(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
