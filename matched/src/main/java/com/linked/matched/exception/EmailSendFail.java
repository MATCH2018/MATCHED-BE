package com.linked.matched.exception;

public class EmailSendFail extends MatchException{

    private static final String MESSAGE = "이메일을 보내는데 실패했습니다.";

    public EmailSendFail() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 0;
    }
}
