package com.linked.matched.controller.user;

import com.linked.matched.request.jwt.TokenDto;
import com.linked.matched.request.user.UserJoin;
import com.linked.matched.request.user.UserLogin;
import com.linked.matched.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<TokenDto> userLogin(@RequestBody UserLogin userLogin) {//토큰 값 response -0
        return ResponseEntity.ok(userService.login(userLogin));
    }

    @PostMapping("/logout")
    public void userLogout(){
    }

    @PostMapping("/join")
    public void userJoin(@RequestBody UserJoin join) throws Exception {
        userService.join(join);
    }

    @PostMapping("/email")
    public void userEmail() {//이메일값 주기 -0 이메일을 주면 특정 값을 줌 그 값으 얻으면 회원가입가능

    }

    @PostMapping("/password_find")
    public void userPasswordFind() {//(password/edit) -0

    }

    @PatchMapping("/my/{id}/edit")//id 값 바꿔주기
    public void userEdit(){//얘도 그냥 edit 각

    }

    @DeleteMapping("/my/{id}/leave")
    public void userDelete(){//그냥 삭제해주면 될듯

    }
    
}
