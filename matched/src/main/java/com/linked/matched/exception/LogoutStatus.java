package com.linked.matched.exception;

public class LogoutStatus extends MatchException{

    private static final String MESSAGE = "로그아웃 된 상태입니다.";

    public LogoutStatus() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 400;
    }
}
