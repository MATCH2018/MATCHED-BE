package com.linked.matched.exception;

public class UserNotFound extends MatchException{

    private static final String MESSAGE = "일치하는 유저가 없습니다.";

    public UserNotFound() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 404;
    }
}
