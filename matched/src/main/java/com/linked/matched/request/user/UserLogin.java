package com.linked.matched.request.user;

import lombok.Builder;
import lombok.Getter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Getter
public class UserLogin {

    private String loginId;
    private String password;

    @Builder
    public UserLogin(String loginId, String password) {
        this.loginId = loginId;
        this.password = password;
    }

    public UsernamePasswordAuthenticationToken toAuthentication(){
        return new UsernamePasswordAuthenticationToken(loginId,password);
    }
}
