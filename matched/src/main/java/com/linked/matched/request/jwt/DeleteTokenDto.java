package com.linked.matched.request.jwt;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DeleteTokenDto {
    private String refreshToken;
    @Builder
    public DeleteTokenDto(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
