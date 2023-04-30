package com.linked.matched.exception;

public class TokenNotFound extends MatchException{

    private static final String MESSAGE= "존재하지 않는 토큰입니다.";

    public TokenNotFound() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 404;
    }
}
