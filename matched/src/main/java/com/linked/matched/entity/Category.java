package com.linked.matched.entity;

import com.linked.matched.entity.status.BoardStatus;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Category {
    @Id @GeneratedValue
    private Long categoryId;
    private BoardStatus categoryName;//세부 카테고리 이름

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @Builder
    public Category(Long categoryId, BoardStatus categoryName) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }
}
