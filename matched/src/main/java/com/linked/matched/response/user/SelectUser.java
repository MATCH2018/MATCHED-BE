package com.linked.matched.response.user;

import lombok.Getter;

@Getter
public class SelectUser {

    private final Long userId;
    private final String name;

    public SelectUser(Long userId, String name) {
        this.userId = userId;
        this.name = name;
    }
}
