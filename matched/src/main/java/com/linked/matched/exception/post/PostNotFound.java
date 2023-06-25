package com.linked.matched.exception.post;


import com.linked.matched.exception.MatchException;

public class PostNotFound extends MatchException {

    private static final String MESSAGE= "존재하지 않는 글입니다.";

    public PostNotFound() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 404;
    }
}
