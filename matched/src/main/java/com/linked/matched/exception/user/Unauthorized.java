package com.linked.matched.exception.user;

import com.linked.matched.exception.MatchException;

public class Unauthorized extends MatchException {

    private static final String MESSAGE = "인증이 필요합니다.";

    public Unauthorized() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 401;
    }
}
