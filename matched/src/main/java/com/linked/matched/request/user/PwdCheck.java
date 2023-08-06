package com.linked.matched.request.user;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PwdCheck {

    private String nowPassword;
    private String checkPassword;

    @Builder
    public PwdCheck(String nowPassword, String checkPassword) {
        this.nowPassword = nowPassword;
        this.checkPassword = checkPassword;
    }
}
