package com.linked.matched.request.user;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PwdChange {

    private String loginId;
    private String newPassword;
    private String checkPassword;

    @Builder
    public PwdChange(String loginId, String newPassword, String checkPassword) {
        this.loginId = loginId;
        this.newPassword = newPassword;
        this.checkPassword = checkPassword;
    }
}
