package com.linked.matched.exception;

public class NotSelfReport extends MatchException {

    private static final String MESSAGE= "자신은 신고 할 수 없습니다.";

    public NotSelfReport() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 400;
    }
}
