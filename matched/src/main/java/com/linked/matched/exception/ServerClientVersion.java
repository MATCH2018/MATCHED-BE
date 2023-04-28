package com.linked.matched.exception;

public class ServerClientVersion extends MatchException{

    private static final String MESSAGE= "서버/클라이언트간 사용하려는 SSL/TLS 버전이 맞지 않습니다.";

    public ServerClientVersion() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 401;
    }
}
