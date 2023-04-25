package com.linked.matched.request.user;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PwdEdit {

    private String nowPassword;
    private String newPassword;
    private String checkPassword;

    @Builder
    public PwdEdit(String nowPassword, String newPassword, String checkPassword) {
        this.nowPassword = nowPassword;
        this.newPassword = newPassword;
        this.checkPassword = checkPassword;
    }
}