package com.linked.matched.request.notice;

import lombok.Builder;
import lombok.Getter;

@Getter
public class NoticeEditor {
    private String title;
    private String content;

    @Builder
    public NoticeEditor(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
