package com.linked.matched.response.user;

import com.linked.matched.entity.User;
import lombok.Getter;

@Getter
public class SelectUser {

    private final Long userId;
    private final String name;

    public SelectUser(User user) {
        this.userId = user.getUserId();
        this.name = user.getName();
    }
}
