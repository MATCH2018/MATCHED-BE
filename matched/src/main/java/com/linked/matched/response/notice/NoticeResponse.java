package com.linked.matched.response.notice;

import com.linked.matched.entity.Notice;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class NoticeResponse {

    private final Long noticeId;
    private final String title;
    private final String content;
    private final LocalDate createdAt;

    public NoticeResponse(Notice notice) {
        this.noticeId = notice.getNoticeId();
        this.title = notice.getTitle();
        this.content = notice.getContent();
        this.createdAt = notice.getCreatedAt();
    }
}
