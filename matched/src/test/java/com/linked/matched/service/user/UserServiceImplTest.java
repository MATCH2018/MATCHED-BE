package com.linked.matched.service.user;

import com.linked.matched.entity.RefreshToken;
import com.linked.matched.entity.User;
import com.linked.matched.repository.jwt.RefreshTokenRepository;
import com.linked.matched.repository.user.UserRepository;
import com.linked.matched.request.jwt.DeleteTokenDto;
import com.linked.matched.request.user.PwdEdit;
import com.linked.matched.request.user.UserEdit;
import com.linked.matched.response.jwt.TokenDto;
import com.linked.matched.request.user.UserJoin;
import com.linked.matched.request.user.UserLogin;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;

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
        userRepository.deleteAll();
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

        Assertions.assertEquals(userRepository.findAll().size(), 1);

        User user = userRepository.findAll().iterator().next();
        Assertions.assertEquals("asd@mju.ac.kr",user.getLoginId());
        Assertions.assertEquals("김씨",user.getName());


    }

    @Test
    @DisplayName("회원가입때 아이디 중복시 회원가입 안됨")
    void test2_1() {
        User user = User.builder()
                .loginId("asd@mju.ac.kr")
                .password("5678")
                .name("이씨")
                .department("컴퓨터공학과")
                .gradle(4)
//                .birth("1999-01-01") //나중에 형변환 해야하는데 지금 ㄴ 중요
                .sex("여성")
                .build();

        userRepository.save(user);

        UserJoin userJoin= UserJoin.builder()
                .loginId("asd@mju.ac.kr")
                .password("1234")
                .checkPassword("1234")
                .name("김씨")
                .department("정보통신과")
                .gradle(3)
//                .birth("1999-01-01") //나중에 형변환 해야하는데 지금 ㄴ 중요
                .sex("남성")
                .build();

        try {
            userService.join(userJoin);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Assertions.assertEquals(userRepository.count(),1L);

        userRepository.findAll().iterator().next();

        Assertions.assertEquals("asd@mju.ac.kr",user.getLoginId());
        Assertions.assertEquals("이씨",user.getName());
        Assertions.assertEquals("컴퓨터공학과",user.getDepartment());

    }

    @Test
    @DisplayName("비밀번호와 체크비밀번호 다름")
    void test2_3(){
        UserJoin userJoin= UserJoin.builder()
                .loginId("아이디입니다")
                .password("1234")
                .checkPassword("5678")
                .name("김씨")
                .department("정보통신과")
                .gradle(3)
//                .birth("1999-01-01") //나중에 형변환 해야하는데 지금 ㄴ 중요
                .sex("남성")
                .build();

        try {
            userService.join(userJoin);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Assertions.assertEquals(userRepository.findAll().size(), 0);

    }

    @Test
    @DisplayName("로그인 성공")
    void test3(){

        UserJoin userJoin= UserJoin.builder()
                .loginId("아이디입니다")
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
                .loginId("아이디입니다")
                .password("1234")
                .build();

        TokenDto user = userService.login(login);

        Assertions.assertNotNull(user);
    }

    @Test
    @DisplayName("jwt refresh 토큰 확인")
    void test4(){
        UserJoin userJoin= UserJoin.builder()
                .loginId("아이디입니다")
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
                .loginId("아이디입니다")
                .password("1234")
                .build();

        TokenDto user = userService.login(login);

        System.out.println(user.getAccessToken());
        System.out.println(user.getRefreshToken());

        RefreshToken next = refreshTokenRepository.findAll().iterator().next();

        Assertions.assertEquals(next.getValue(),user.getRefreshToken());
    }
    
    @Test
    @DisplayName("jwt 토큰 유효기간이 지났을 경우 로그인 안됨")
    void test5(){

    }

    @Test
    @DisplayName("accessToken 기간이 지나서 refreshToken이 사용가능한가")
    void test6(){

    }

    @Test
    @DisplayName("refreshToken 삭제")
    void test7(){
        UserJoin userJoin= UserJoin.builder()
                .loginId("아이디입니다")
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
                .loginId("아이디입니다")
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
        UserJoin userJoin= UserJoin.builder()
                .loginId("아이디입니다")
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

        Assertions.assertEquals(userRepository.findAll().size(), 1);

        User user = userRepository.findAll().iterator().next();

        UserEdit userEdit = UserEdit.builder()
                .name("최씨")
                .department("컴퓨터공학과")
                .gradle(2)
                .build();

//        userService.edit(user.getUserId(),userEdit);
//
//        User edit = userRepository.findAll().iterator().next();
//        Assertions.assertEquals(userRepository.findAll().size(), 1);
//
//        Assertions.assertEquals(edit.getName(),"최씨");
//        Assertions.assertEquals(edit.getDepartment(),"컴퓨터공학과");
//        //여기서 null이 뜬다. null이 안뜨도록 수정해줘야한다.
//        Assertions.assertEquals(edit.getLoginId(),"아이디입니다");
    }

    @Test
    @DisplayName("회원 삭제")
    void test9(){
        UserJoin userJoin= UserJoin.builder()
                .loginId("아이디입니다")
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

        User next = userRepository.findAll().iterator().next();

//        userService.deleteUser(next.getUserId());
//
//        Assertions.assertEquals(userRepository.count(),0);
    }

    @Test
    @DisplayName("비밀번호 변경")
    void test10(){

        UserJoin userJoin= UserJoin.builder()
                .loginId("아이디입니다")
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


        User next = userRepository.findAll().iterator().next();

        PwdEdit newPassword = PwdEdit.builder()
                .nowPassword("1234")
                .newPassword("5678")
                .checkPassword("5678")
                .build();

//        userService.passwordEdit(next.getUserId(), newPassword);
//
//        UserLogin login = UserLogin.builder()
//                .loginId("아이디입니다")
//                .password("5678")
//                .build();
//
//        TokenDto user = userService.login(login);
//
//        Assertions.assertNotNull(user);
    }
}