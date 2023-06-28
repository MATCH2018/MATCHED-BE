package com.linked.matched.exception.user;

import com.linked.matched.exception.MatchException;

public class JwtSignatureException extends MatchException {

    private static final String MESSAGE = "잘못된 JWT 서명입니다.";

    public JwtSignatureException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 401;
    }
}
