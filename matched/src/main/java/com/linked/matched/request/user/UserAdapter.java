package com.linked.matched.request.user;

import com.linked.matched.config.jwt.CustomUserDetails;
import com.linked.matched.entity.User;
import lombok.Getter;

@Getter
public class UserAdapter extends CustomUserDetails {

    private User user;

    public UserAdapter(User user) {
        super(user);
        this.user=user;
    }
}
