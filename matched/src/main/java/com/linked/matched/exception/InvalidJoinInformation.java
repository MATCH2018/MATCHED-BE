package com.linked.matched.exception;

public class InvalidJoinInformation extends MatchException {
    private static final String MESSAGE = "학교이메일을 이용해주세요.";

    public InvalidJoinInformation() {
        super(MESSAGE);
    }

    public InvalidJoinInformation(String fieldName, String message) {
        super(MESSAGE);
        addValidation(fieldName, message);
    }

    @Override
    public int getStatusCode() {
        return 400;
    }
}
