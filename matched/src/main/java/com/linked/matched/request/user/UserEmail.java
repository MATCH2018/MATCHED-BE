package com.linked.matched.request.user;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserEmail {

    private String email;

    @Builder
    public UserEmail(String email) {
        this.email = email;
    }

    public boolean isValid(){
        String emailPattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@mju\\.ac\\.kr$";
        return email != null && email.matches(emailPattern);
    }

}
