package com.linked.matched.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@NoArgsConstructor
@Entity
@Getter
public class PostReport {
    @Id @GeneratedValue
    private Long id;

    private Long reporterId;

    private Long reportPostId;

    private String content;

    @Builder
    public PostReport(Long id, Long reporterId, Long reportPostId, String content) {
        this.id = id;
        this.reporterId = reporterId;
        this.reportPostId = reportPostId;
        this.content = content;
    }
}
