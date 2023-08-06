package com.linked.matched.service.user;

import com.linked.matched.request.jwt.DeleteTokenDto;
import com.linked.matched.request.jwt.TokenRequestDto;
import com.linked.matched.request.user.*;
import com.linked.matched.response.jwt.TokenDto;
import com.linked.matched.response.user.UserMail;
import com.linked.matched.response.user.UserProfile;

public interface UserService {
    void join(UserJoin userJoin) throws Exception;

    TokenDto login(UserLogin userLogin);

    TokenDto reissue(TokenRequestDto tokenRequestDto);

    void refreshTokenDelete(DeleteTokenDto deleteTokenDto);

    void deleteUser(Long id);

    void edit(Long id, UserEditor userEdit);

    boolean passwordCheck(Long id, PwdCheck pwdEdit);

    void passwordChange(PwdChange pwdChange);

    UserMail findUserEmail(Long applicantId);

     UserProfile viewUser(Long id);
}
