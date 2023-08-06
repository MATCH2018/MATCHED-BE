package com.linked.matched.controller.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.linked.matched.annotation.WithAuthUser;
import com.linked.matched.entity.User;
import com.linked.matched.exception.user.UserNotFound;
import com.linked.matched.repository.user.UserRepository;
import com.linked.matched.request.user.*;
import com.linked.matched.service.user.UserService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @AfterEach
    void clean() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("회원 가입")
    void test1() throws Exception {
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

        String json = objectMapper.writeValueAsString(userJoin);

        mockMvc.perform(MockMvcRequestBuilders.post("/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());


        User user = userRepository.findByLoginId("asd@mju.ac.kr").orElseThrow(UserNotFound::new);

        Assertions.assertEquals(user.getName(),"김씨");
        Assertions.assertEquals(user.getDepartment(),"정보통신과");
    }

    @Test
    @DisplayName("로그인 성공")
    void test2() throws Exception {
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

        String json = objectMapper.writeValueAsString(login);

        mockMvc.perform(MockMvcRequestBuilders.post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("로그인 실패")
    void test3() throws Exception {
        User user= User.builder()
                .loginId("asd@mju.ac.kr")
                .password("1234")
                .name("김씨")
                .department("정보통신과")
                .gradle(3)
//                .birth("1999-01-01") //나중에 형변환 해야하는데 지금 ㄴ 중요
                //               .roleStatus(RoleStatus.ROLE_USER)
                .sex("남성")
                .build();

        userRepository.save(user);

        UserLogin login = UserLogin.builder()
                .loginId("asd@mju.ac.kr")
                .password("5678")
                .build();

        String json = objectMapper.writeValueAsString(login);

        mockMvc.perform(MockMvcRequestBuilders.post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized())
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    @DisplayName("@mju.ac.kr 메일 아니면 회원 가입 안됨")
    void test4() throws Exception {
        UserJoin userJoin= UserJoin.builder()
                .loginId("asd@naver.com")
                .password("1234")
                .checkPassword("1234")
                .name("김씨")
                .department("정보통신과")
                .gradle(3)
//                .birth("1999-01-01") //나중에 형변환 해야하는데 지금 ㄴ 중요
                .sex("남성")
                .build();

        String json = objectMapper.writeValueAsString(userJoin);

        mockMvc.perform(MockMvcRequestBuilders.post("/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(MockMvcResultHandlers.print());

        Assertions.assertEquals(userRepository.count(),0);
    }

    @Test
    @DisplayName("로그인 안 되었을 때 비밀번호 변경")
    void test5() throws Exception {

        User user= User.builder()
                .loginId("asd@mju.ac.kr")
                .password("1234")
                .name("김씨")
                .department("정보통신과")
                .gradle(3)
//                .birth("1999-01-01") //나중에 형변환 해야하는데 지금 ㄴ 중요
                //               .roleStatus(RoleStatus.ROLE_USER)
                .sex("남성")
                .build();

        userRepository.save(user);

        PwdChange change = PwdChange.builder()
                .loginId("asd@mju.ac.kr")
                .newPassword("5678")
                .checkPassword("5678")
                .build();

        String json = objectMapper.writeValueAsString(change);

        mockMvc.perform(MockMvcRequestBuilders.post("/password_change")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());


        User userTest = userRepository.findByLoginId("asd@mju.ac.kr").orElseThrow(() -> new UserNotFound());
        passwordEncoder.matches("5678",userTest.getPassword());
    }

    @Test
    @DisplayName("회원 정보 수정")
    @WithAuthUser
    void test6()throws Exception{

        UserEditor edit=UserEditor.builder()
                .sex("남성")
                .name("이름")
                .department("통신")
                .build();

        String json = objectMapper.writeValueAsString(edit);

        mockMvc.perform(MockMvcRequestBuilders.patch("/my")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());

        User user = userRepository.findByLoginId("asd@mju.ac.kr").orElseThrow(() -> new UserNotFound());

        Assertions.assertEquals(user.getName(),"이름");
    }



    @Test
    @DisplayName("회원 탈퇴")
    @WithAuthUser
    void test7()throws Exception{

        UserEditor edit=UserEditor.builder()
                .sex("남성")
                .name("이름")
                .department("통신")
                .build();

        String json = objectMapper.writeValueAsString(edit);

        mockMvc.perform(MockMvcRequestBuilders.delete("/my")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());

        Assertions.assertEquals(userRepository.count(),0);
    }


}