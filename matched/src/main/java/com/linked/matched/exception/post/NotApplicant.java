package com.linked.matched.exception.post;

import com.linked.matched.exception.MatchException;

public class NotApplicant extends MatchException {

    private static final String MESSAGE = "지원한 게시글이 아닙니다.";

    public NotApplicant() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 404;
    }
}
