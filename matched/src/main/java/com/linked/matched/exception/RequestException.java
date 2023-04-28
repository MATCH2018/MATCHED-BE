package com.linked.matched.exception;

public class RequestException extends MatchException{

    private static final String MESSAGE= "요청 오류입니다.";

    public RequestException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 0;
    }
}
