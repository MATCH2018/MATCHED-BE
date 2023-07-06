package com.linked.matched.config.jwt;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;

public class UserPrincipal extends User {

    private final Long userId;

    public UserPrincipal(com.linked.matched.entity.User user){
        super(user.getLoginId(), user.getPassword(), List.of(new SimpleGrantedAuthority("ROLE_USER")));
        this.userId=user.getUserId();
    }

    public Long getUserId() {
        return userId;
    }
}
