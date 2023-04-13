package com.linked.matched.service.user;

import com.linked.matched.response.jwt.TokenDto;
import com.linked.matched.request.user.UserJoin;
import com.linked.matched.request.user.UserLogin;

public interface UserService {
    public void join(UserJoin userJoin) throws Exception;

    public TokenDto login(UserLogin userLogin);
}
