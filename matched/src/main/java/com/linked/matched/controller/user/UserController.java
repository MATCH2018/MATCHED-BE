package com.linked.matched.controller.user;

import com.linked.matched.request.jwt.DeleteTokenDto;
import com.linked.matched.request.jwt.TokenRequestDto;
import com.linked.matched.request.user.PwdEdit;
import com.linked.matched.request.user.UserEdit;
import com.linked.matched.response.jwt.TokenDto;
import com.linked.matched.request.user.UserJoin;
import com.linked.matched.request.user.UserLogin;
import com.linked.matched.service.user.EmailService;
import com.linked.matched.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public void userLogout(@RequestBody DeleteTokenDto deleteTokenDto){
        userService.refreshTokenDelete(deleteTokenDto);
    }

    @PostMapping("/join")
    public void userJoin(@RequestBody UserJoin join) throws Exception {
        userService.join(join);
    }

    @GetMapping("/email")
    public ResponseEntity<String> userEmail(@RequestParam("email") String email) throws Exception {//이메일값 주기 -0 이메일을 주면 특정 값을 줌 그 값으 얻으면 회원가입가능
        String confirm = emailService.sendSimpleMessage(email);

        return ResponseEntity.ok(confirm);
    }

    @PostMapping("/{userId}/password_change")
    public void userPasswordFind(@PathVariable Long userId,@RequestBody PwdEdit pwdEdit) {//(password/edit) -0
        userService.passwordEdit(userId,pwdEdit);
    }

    @PatchMapping("/my/{userId}")//id 값 바꿔주기
    public void userEdit(@PathVariable Long userId, @RequestBody UserEdit userEdit){//얘도 그냥 edit 각
        userService.edit(userId, userEdit);
    }

    @DeleteMapping("/my/{userId}")
    public void userDelete(@PathVariable Long userId){//그냥 삭제해주면 될듯
        userService.deleteUser(userId);
    }
    
}
