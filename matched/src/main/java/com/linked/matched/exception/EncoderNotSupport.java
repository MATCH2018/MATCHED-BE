package com.linked.matched.exception;

public class EncoderNotSupport extends MatchException{

    private static final String MESSAGE = "지원하지 않는 인코더입니다.";


    public EncoderNotSupport() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 401;
    }
}
