package com.linked.matched.service.user;

import com.linked.matched.request.jwt.DeleteTokenDto;
import com.linked.matched.request.jwt.TokenRequestDto;
import com.linked.matched.request.user.PwdEdit;
import com.linked.matched.request.user.UserEdit;
import com.linked.matched.response.jwt.TokenDto;
import com.linked.matched.request.user.UserJoin;
import com.linked.matched.request.user.UserLogin;
import com.linked.matched.response.user.UserMail;
import com.linked.matched.response.user.UserProfile;

public interface UserService {
    void join(UserJoin userJoin) throws Exception;

    TokenDto login(UserLogin userLogin);

    TokenDto reissue(TokenRequestDto tokenRequestDto);


    void refreshTokenDelete(DeleteTokenDto deleteTokenDto);

    void deleteUser(Long userId);

    void edit(Long userId, UserEdit userEdit);

    void passwordEdit(Long userId, PwdEdit pwdEdit);

    UserMail findUserEmail(Long applicantId);

     UserProfile viewUser(Long userId);
}
