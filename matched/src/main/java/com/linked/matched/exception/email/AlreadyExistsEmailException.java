package com.linked.matched.exception.email;

import com.linked.matched.exception.MatchException;

public class AlreadyExistsEmailException extends MatchException {

    private static final String MESSAGE = "이미 가입된 이메일입니다.";

    public AlreadyExistsEmailException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 400;
    }
}

