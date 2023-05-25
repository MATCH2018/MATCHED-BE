package com.linked.matched.controller.user;

import com.linked.matched.request.jwt.DeleteTokenDto;
import com.linked.matched.request.jwt.TokenRequestDto;
import com.linked.matched.request.user.*;
import com.linked.matched.response.ResponseDto;
import com.linked.matched.response.jwt.TokenDto;
import com.linked.matched.response.user.UserProfile;
import com.linked.matched.service.user.EmailService;
import com.linked.matched.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final EmailService emailService;

    @PostMapping("/login") //받는값 바꾸어야 할 거 같다.
    public ResponseEntity<TokenDto> userLogin(@RequestBody UserLogin userLogin) {//토큰 값 response -0
        return ResponseEntity.ok(userService.login(userLogin));
    }

    @PostMapping("/reissue") //받는 값 한번 다시 생각해보자
    public ResponseEntity<TokenDto> reissue(@RequestBody TokenRequestDto tokenRequestDto) {
        return ResponseEntity.ok(userService.reissue(tokenRequestDto));
    }

    @PostMapping("/logout")
    public ResponseEntity<Object> userLogout(@RequestBody DeleteTokenDto deleteTokenDto){ // 로그아웃 되었습니다.
        userService.refreshTokenDelete(deleteTokenDto);
        return new ResponseEntity<>(new ResponseDto("로그아웃 되었습니다."), HttpStatus.OK);

    }

    @PostMapping("/join")
    public ResponseEntity<Object> userJoin(@RequestBody UserJoin join) throws Exception { // 회원가입 되었습니다.
//        if(join.isValid()) {
            userService.join(join);
            return new ResponseEntity<>(new ResponseDto("회원가입 되었습니다."), HttpStatus.OK);
//        }

//        return new ResponseEntity<>(new ResponseDto("명지대 이메일을 이용해주세요"), HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/email")
    public ResponseEntity<Object> userEmail(@RequestParam("email") UserEmail email) throws Exception {//이메일값 주기 -0 이메일을 주면 특정 값을 줌 그 값으 얻으면 회원가입가능

        if(email.isValid()) {
            String confirm = emailService.sendSimpleMessage(email);
            return new ResponseEntity<>(new ResponseDto(confirm), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ResponseDto("명지대 이메일을 이용해주세요"), HttpStatus.BAD_REQUEST);

    }

    @PostMapping("/password_change")
    public ResponseEntity<Object> userPasswordFind(@RequestBody PwdEdit pwdEdit, Principal principal) {//비밀번호가 변경되었습니다.
        userService.passwordEdit(principal,pwdEdit);
        return new ResponseEntity<>(new ResponseDto("비밀번호가 변경 되었습니다."), HttpStatus.OK);

    }

    @PatchMapping("/my")//id 값 바꿔주기
    public ResponseEntity<Object> userEdit(@RequestBody UserEdit userEdit,Principal principal){//회원정보가 수정되었습니다.
        userService.edit(principal, userEdit);
        return new ResponseEntity<>(new ResponseDto("회원정보가 수정 되었습니다."), HttpStatus.OK);

    }

    @DeleteMapping("/my") // 회원탈퇴 되었습니다.
    public ResponseEntity<Object> userDelete(Principal principal){//그냥 삭제해주면 될듯
        userService.deleteUser(principal);
        return new ResponseEntity<>(new ResponseDto("회원탈퇴 되었습니다."), HttpStatus.OK);

    }
    
    //누르면 회원정보를 보게하는 것
    @GetMapping("/profile")
    public UserProfile profileFind(Principal principal){
        return userService.viewUser(principal);
    }
}
