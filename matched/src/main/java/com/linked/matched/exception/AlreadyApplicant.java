package com.linked.matched.exception;

public class AlreadyApplicant extends MatchException{

    private static final String MESSAGE= "이미 지원한 게시글입니다.";
    public AlreadyApplicant() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 0;
    }
}
