package com.linked.matched.request.jwt;

import lombok.Builder;
import lombok.Getter;

@Getter
public class DeleteTokenDto {
    private String refreshToken;
    @Builder
    public DeleteTokenDto(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
