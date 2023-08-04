package com.linked.matched.service.user;

import com.linked.matched.entity.RefreshToken;
import com.linked.matched.entity.User;
import com.linked.matched.exception.user.InvalidLoginInformation;
import com.linked.matched.exception.user.UserNotFound;
import com.linked.matched.repository.jwt.RefreshTokenRepository;
import com.linked.matched.repository.user.UserRepository;
import com.linked.matched.request.jwt.DeleteTokenDto;
import com.linked.matched.request.user.PwdCheck;
import com.linked.matched.request.user.UserEditor;
import com.linked.matched.request.user.UserJoin;
import com.linked.matched.request.user.UserLogin;
import com.linked.matched.response.jwt.TokenDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Optional;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class UserServiceImplTest {
    
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @AfterEach
    void clean(){
        Optional<User> user = userRepository.findByLoginId("asd@mju.ac.kr");
        if(user.isPresent()) {
            User userDelete = user.get();
            userRepository.delete(userDelete);
        }
        refreshTokenRepository.deleteAll();
    }
    
    @Test
    @DisplayName("회원가입 성공")
    void test1() throws Exception {
        UserJoin userJoin= UserJoin.builder()
                .loginId("asd@mju.ac.kr")
                .password("1234")
                .checkPassword("1234")
                .name("김씨")
                .department("정보통신과")
                .gradle(3)
                .sex("남성")
                .build();

        userService.join(userJoin);

        User user = userRepository.findByLoginId("asd@mju.ac.kr").orElseThrow(()->new UserNotFound());
        Assertions.assertEquals("asd@mju.ac.kr",user.getLoginId());
        Assertions.assertEquals("김씨",user.getName());


    }

    @Test
    @DisplayName("회원가입때 아이디 중복시 회원가입 안됨")
    void test2_1() {
        User usertest = User.builder()
                .loginId("asd@mju.ac.kr")
                .password("5678")
                .name("이씨")
                .department("컴퓨터공학과")
                .gradle(4)
                .sex("여성")
                .build();

        userRepository.save(usertest);

        UserJoin userJoin= UserJoin.builder()
                .loginId("asd@mju.ac.kr")
                .password("1234")
                .checkPassword("1234")
                .name("김씨")
                .department("정보통신과")
                .gradle(3)
                .sex("남성")
                .build();

        try {
            userService.join(userJoin);
        } catch (Exception e) {
            e.printStackTrace();
        }

        User user = userRepository.findByLoginId("asd@mju.ac.kr").orElseThrow(() -> new UserNotFound());

        Assertions.assertEquals("asd@mju.ac.kr",user.getLoginId());
        Assertions.assertEquals("이씨",user.getName());
        Assertions.assertEquals("컴퓨터공학과",user.getDepartment());

    }

    @Test
    @DisplayName("비밀번호와 체크비밀번호 다름")
    void test2_3(){
        UserJoin userJoin= UserJoin.builder()
                .loginId("asd@mju.ac.kr")
                .password("1234")
                .checkPassword("5678")
                .name("김씨")
                .department("정보통신과")
                .gradle(3)
//                .birth("1999-01-01") //나중에 형변환 해야하는데 지금 ㄴ 중요
                .sex("남성")
                .build();

        Assertions.assertThrows(InvalidLoginInformation.class,()->{
            userService.join(userJoin);
        });
    }

    @Test
    @DisplayName("로그인 성공")
    void test3(){

        UserJoin userJoin= UserJoin.builder()
                .loginId("asd@mju.ac.kr")
                .password("1234")
                .checkPassword("1234")
                .name("김씨")
                .department("정보통신과")
                .gradle(3)
//                .birth("1999-01-01") //나중에 형변환 해야하는데 지금 ㄴ 중요
 //               .roleStatus(RoleStatus.ROLE_USER)
                .sex("남성")
                .build();

        try {
            userService.join(userJoin);
        } catch (Exception e) {
            e.printStackTrace();
        }

        UserLogin login = UserLogin.builder()
                .loginId("asd@mju.ac.kr")
                .password("1234")
                .build();

        TokenDto user = userService.login(login);

        Assertions.assertNotNull(user);
    }

    @Test
    @DisplayName("jwt refresh 토큰 확인")
    void test4(){
        UserJoin userJoin= UserJoin.builder()
                .loginId("asd@mju.ac.kr")
                .password("1234")
                .checkPassword("1234")
                .name("김씨")
                .department("정보통신과")
                .gradle(3)
//                .birth("1999-01-01") //나중에 형변환 해야하는데 지금 ㄴ 중요
//               .roleStatus(RoleStatus.ROLE_USER)
                .sex("남성")
                .build();

        try {
            userService.join(userJoin);
        } catch (Exception e) {
            e.printStackTrace();
        }

        UserLogin login = UserLogin.builder()
                .loginId("asd@mju.ac.kr")
                .password("1234")
                .build();

        TokenDto user = userService.login(login);

        RefreshToken next = refreshTokenRepository.findAll().iterator().next();

        Assertions.assertEquals(next.getValue(),user.getRefreshToken());
    }

    @Test
    @DisplayName("refreshToken 삭제")
    void test7(){
        UserJoin userJoin= UserJoin.builder()
                .loginId("asd@mju.ac.kr")
                .password("1234")
                .checkPassword("1234")
                .name("김씨")
                .department("정보통신과")
                .gradle(3)
//                .birth("1999-01-01") //나중에 형변환 해야하는데 지금 ㄴ 중요
//               .roleStatus(RoleStatus.ROLE_USER)
                .sex("남성")
                .build();

        try {
            userService.join(userJoin);
        } catch (Exception e) {
            e.printStackTrace();
        }

        UserLogin login = UserLogin.builder()
                .loginId("asd@mju.ac.kr")
                .password("1234")
                .build();

        TokenDto user = userService.login(login);

        RefreshToken next = refreshTokenRepository.findAll().iterator().next();

        Assertions.assertEquals(next.getValue(),user.getRefreshToken());

        DeleteTokenDto deleteToken = DeleteTokenDto.builder()
                .refreshToken(next.getValue())
                .build();

        userService.refreshTokenDelete(deleteToken);

        Assertions.assertEquals(refreshTokenRepository.count(),0);
    }

    @Test
    @DisplayName("회원 정보 수정")
    void test8(){
        User userJoin= User.builder()
                .loginId("asd@mju.ac.kr")
                .password("1234")
                .name("김씨")
                .department("정보통신과")
                .gradle(3)
//                .birth("1999-01-01") //나중에 형변환 해야하는데 지금 ㄴ 중요
//               .roleStatus(RoleStatus.ROLE_USER)
                .sex("남성")
                .build();

        User user = userRepository.save(userJoin);

        UserEditor userEdit = UserEditor.builder()
                .name("최씨")
                .department("컴퓨터공학과")
                .gradle(2)
                .build();

        userService.edit(user.getUserId(),userEdit);

        User edit = userRepository.findByLoginId(user.getLoginId()).orElseThrow(()->new UserNotFound());

        Assertions.assertEquals(edit.getName(),"최씨");
        Assertions.assertEquals(edit.getDepartment(),"컴퓨터공학과");
        Assertions.assertEquals(edit.getLoginId(),"asd@mju.ac.kr");
    }

    @Test
    @DisplayName("본인체크")
    void test10(){

        User userJoin= User.builder()
                .loginId("asd@mju.ac.kr")
                .password(passwordEncoder.encode("1234"))
                .name("김씨")
                .department("정보통신과")
                .gradle(3)
//                .birth("1999-01-01") //나중에 형변환 해야하는데 지금 ㄴ 중요
//               .roleStatus(RoleStatus.ROLE_USER)
                .sex("남성")
                .build();

        userRepository.save(userJoin);


        User next = userRepository.findByLoginId(userJoin.getLoginId()).orElseThrow(()->new UserNotFound());

        PwdCheck newPassword = PwdCheck.builder()
                .nowPassword("1234")
                .checkPassword("1234")
                .build();

        userService.passwordCheck(next.getUserId(), newPassword);

        UserLogin login = UserLogin.builder()
                .loginId("asd@mju.ac.kr")
                .password("1234")
                .build();

        TokenDto user = userService.login(login);

        Assertions.assertNotNull(user);
    }
}