package com.linked.matched.exception;

public class RoomNotFound extends MatchException{
    private static final String MESSAGE = "존재하지 않는 채팅방입니다.";

    public RoomNotFound() {
        super(MESSAGE);
    }

    public RoomNotFound(String message) {
        super(message);
    }

    @Override
    public int getStatusCode() {
        return 404;
    }
}
