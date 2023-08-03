package com.linked.matched.exception.post;

import com.linked.matched.exception.MatchException;

public class SelfApplicant extends MatchException {

    private static final String MESSAGE= "자신의 post는 자신이 신청할 수 없습니다.";
    public SelfApplicant() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 400;
    }
}
