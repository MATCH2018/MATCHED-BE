package com.linked.matched.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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
    private Date date;

    @Builder
    public Notice(Long noticeId, String title, String content, Date date) {
        this.noticeId = noticeId;
        this.title = title;
        this.content = content;
        this.date = date;
    }
}
