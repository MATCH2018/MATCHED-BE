package com.linked.matched.entity;

import com.linked.matched.entity.status.WishStatus;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class InterestCategory {
    @Id
    @GeneratedValue
    private Long interestCategoryId;
    private Date createdAt;
    private Date updatedAt;
    @Enumerated(EnumType.STRING)
    private WishStatus status;

    @Builder
    public InterestCategory(Long interestCategoryId, Date createdAt, Date updatedAt, WishStatus status) {
        this.interestCategoryId = interestCategoryId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.status = status;
    }
}
