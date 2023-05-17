package com.linked.matched.request.report;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostReportRequest {

    private Long reportedPostId;
    private String content;
}
