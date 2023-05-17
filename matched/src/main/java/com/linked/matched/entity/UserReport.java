package com.linked.matched.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class UserReport {

    @Id @GeneratedValue
    private Long id;

    private Long reporterId;

    private Long reporterUserId;
    
    //신고 횟수를 지정해서 그 횟수가 넘으면 정지각

    private String content;

    private LocalDate createAt;

    @Builder
    public UserReport(Long id, Long reporterId, Long reporterUserId, String content) {
        this.id = id;
        this.reporterId = reporterId;
        this.reporterUserId = reporterUserId;
        this.content = content;
        this.createAt = LocalDate.now();
    }


}
