package com.linked.matched.controller.user;

import com.linked.matched.config.jwt.UserPrincipal;
import com.linked.matched.request.jwt.DeleteTokenDto;
import com.linked.matched.request.jwt.TokenRequestDto;
import com.linked.matched.request.user.*;
import com.linked.matched.response.ResponseDto;
import com.linked.matched.response.jwt.TokenDto;
import com.linked.matched.response.user.UserProfile;
import com.linked.matched.service.user.BlacklistService;
import com.linked.matched.service.user.EmailService;
import com.linked.matched.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final EmailService emailService;
    private final BlacklistService blacklistService;

    @PostMapping("/login") //로그인
    public ResponseEntity<Object> userLogin(@RequestBody UserLogin userLogin) {//토큰 값 response -0
        if(blacklistService.blacklist(userLogin.getLoginId())) {
            return ResponseEntity.ok(userService.login(userLogin));
        }
        return new ResponseEntity<>(new ResponseDto("정지된 회원입니다"), HttpStatus.CONFLICT);
    }

    @PostMapping("/reissue") //토큰 값 받기
    public ResponseEntity<TokenDto> reissue(@RequestBody TokenRequestDto tokenRequestDto) {
        return ResponseEntity.ok(userService.reissue(tokenRequestDto));
    }

    @PostMapping("/logout")//로그아웃
    public ResponseEntity<Object> userLogout(@RequestBody DeleteTokenDto deleteTokenDto){ // 로그아웃 되었습니다.
        userService.refreshTokenDelete(deleteTokenDto);
        return new ResponseEntity<>(new ResponseDto("로그아웃 되었습니다."), HttpStatus.OK);

    }

    @PostMapping("/join") // 회원가입
    public ResponseEntity<Object> userJoin(@RequestBody UserJoin join) throws Exception { // 회원가입 되었습니다.
        if(join.isValid()) {
            userService.join(join);
            return new ResponseEntity<>(new ResponseDto("회원가입 되었습니다."), HttpStatus.OK);
        }

        return new ResponseEntity<>(new ResponseDto("명지대 이메일을 이용해주세요"), HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/email")// 이메일 인증
    public String userEmail(@RequestBody UserEmail email) throws Exception {//이메일값 주기 -0 이메일을 주면 특정 값을 줌 그 값으 얻으면 회원가입가능

        if(email.isValid()) {
            return emailService.sendSimpleMessage(email);
        }
        return "명지대 이메일을 이용해주세요";

    }

    @PostMapping("/password_edit")//로그인 되었을때 비밀번호 변경
    public ResponseEntity<Object> userPasswordEdit(@RequestBody PwdEdit pwdEdit, @AuthenticationPrincipal UserPrincipal userPrincipal) {//비밀번호가 변경되었습니다.
        userService.passwordEdit(userPrincipal.getUserId(),pwdEdit);
        return new ResponseEntity<>(new ResponseDto("비밀번호가 변경 되었습니다."), HttpStatus.OK);

    }

    @PostMapping("/password_change")//로그인 안되었을때 비밀번호 변경
    public ResponseEntity<Object> userPasswordChange(@RequestBody PwdChange pwdChange) {//비밀번호가 변경되었습니다.
        userService.passwordChange(pwdChange);
        return new ResponseEntity<>(new ResponseDto("비밀번호가 변경 되었습니다."), HttpStatus.OK);

    }

    @PatchMapping("/my")//회원정보 수정
    public ResponseEntity<Object> userEdit(@RequestBody UserEditor userEdit,@AuthenticationPrincipal UserPrincipal userPrincipal){//회원정보가 수정되었습니다.
        userService.edit(userPrincipal.getUserId(), userEdit);
        return new ResponseEntity<>(new ResponseDto("회원정보가 수정 되었습니다."), HttpStatus.OK);

    }

    @DeleteMapping("/my") // 회원탈퇴 
    public ResponseEntity<Object> userDelete(@AuthenticationPrincipal UserPrincipal userPrincipal){//그냥 삭제해주면 될듯
        userService.deleteUser(userPrincipal.getUserId());
        return new ResponseEntity<>(new ResponseDto("회원탈퇴 되었습니다."), HttpStatus.OK);

    }
    
    @GetMapping("/profile")//회원정보 조회
    public UserProfile profileFind(UserPrincipal userPrincipal){
        return userService.viewUser(userPrincipal.getUserId());
    }

}
