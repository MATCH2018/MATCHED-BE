package com.linked.matched.request.report;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserReportRequest {

    //한번 봐야할것 같다.
    //notnull 라이브러리 받아야한다. 또한 한번 더 처리해야한다.
    private Long reportUserId;// 신고당하는 사람
    private String content;
}
