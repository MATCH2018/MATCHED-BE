package com.linked.matched.exception;

public class NotSecurityContext extends MatchException{

    private static final String MESSAGE= "Security Context 에 인자 정보가 없습니다.";

    public NotSecurityContext() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 400;
    }
}
