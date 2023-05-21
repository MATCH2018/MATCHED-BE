package com.linked.matched.response.user;

import com.linked.matched.entity.User;
import lombok.Getter;

@Getter
public class UserMail {
    private final Long userId;
    private final String loginId;

    public UserMail(User user) {
        this.userId = user.getUserId();
        this.loginId = user.getLoginId();
    }
}
