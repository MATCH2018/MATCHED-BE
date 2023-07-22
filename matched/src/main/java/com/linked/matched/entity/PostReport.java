package com.linked.matched.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Entity
@Getter
public class PostReport {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long reporterId;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @Builder
    public PostReport(Long id, Long reporterId, String content, Post post) {
        this.id = id;
        this.reporterId = reporterId;
        this.content = content;
        this.post = post;
    }
}
