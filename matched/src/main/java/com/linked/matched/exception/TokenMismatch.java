package com.linked.matched.exception;

public class TokenMismatch extends MatchException{

    private static final String MESSAGE = "토큰의 정보가 일치하지 않습니다.";

    public TokenMismatch() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 0;
    }
}
