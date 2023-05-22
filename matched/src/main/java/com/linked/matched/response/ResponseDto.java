package com.linked.matched.response;

import lombok.Getter;

@Getter
public class ResponseDto {

    private String content;

    public ResponseDto(String content) {
        this.content = content;
    }
}
