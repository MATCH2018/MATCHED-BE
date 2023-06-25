package com.linked.matched.exception.user;

import com.linked.matched.exception.MatchException;

public class AuthenticationFail extends MatchException {

    private static final String MESSAGE = "인증에 실패 했습니다.";

    public AuthenticationFail() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 401;
    }
}
