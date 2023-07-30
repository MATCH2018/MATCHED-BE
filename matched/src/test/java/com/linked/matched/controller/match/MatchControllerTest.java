package com.linked.matched.controller.match;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.linked.matched.annotation.WithAuthUser;
import com.linked.matched.entity.Applicant;
import com.linked.matched.entity.Post;
import com.linked.matched.entity.User;
import com.linked.matched.entity.status.BoardStatus;
import com.linked.matched.exception.user.UserNotFound;
import com.linked.matched.repository.match.ApplicantRepository;
import com.linked.matched.repository.post.PostRepository;
import com.linked.matched.repository.user.UserRepository;
import com.linked.matched.request.post.PostEditor;
import com.linked.matched.service.match.ApplicantService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class MatchControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private ApplicantRepository applicantRepository;

    @Autowired
    private PostRepository postRepository;

    @AfterEach
    void clean(){
        userRepository.deleteAll();
        applicantRepository.deleteAll();
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("내 게시글 목록 조회")
    @WithAuthUser
    void test1() throws Exception {

        User user = userRepository.findByLoginId("asd@mju.ac.kr").orElseThrow(() -> new UserNotFound());

        Post post= Post.builder()
                .title("test")
                .content("test")
                .boardName(BoardStatus.valueOf("club"))
                .user(user)
                .build();

        postRepository.save(post);

        Post post2= Post.builder()
                .title("test2")
                .content("test2")
                .boardName(BoardStatus.valueOf("club"))
                .user(user)
                .build();

        postRepository.save(post2);

        mockMvc.perform(MockMvcRequestBuilders.get("/match")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("게시글 자세히 보기")
    @WithAuthUser
    void test2() throws Exception {

        User user = userRepository.findByLoginId("asd@mju.ac.kr").orElseThrow(() -> new UserNotFound());

        Post post= Post.builder()
                .title("test")
                .content("test")
                .boardName(BoardStatus.valueOf("club"))
                .user(user)
                .build();

        postRepository.save(post);

        mockMvc.perform(MockMvcRequestBuilders.get("/match/my/{postId}",post.getPostId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("내 게시글 수정하기")
    @WithAuthUser
    void test3() throws Exception {
        User user = userRepository.findByLoginId("asd@mju.ac.kr").orElseThrow(() -> new UserNotFound());

        Post post= Post.builder()
                .title("test")
                .content("test")
                .boardName(BoardStatus.valueOf("club"))
                .user(user)
                .build();

        postRepository.save(post);

        PostEditor postEdit = PostEditor.builder()
                .title("test2")
                .content("test2")
                .build();

        mockMvc.perform(MockMvcRequestBuilders.patch("/match/my/{postId}",post.getPostId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postEdit)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());

        Post postFind = postRepository.findById(post.getPostId()).orElseThrow(() -> new UserNotFound());

        Assertions.assertEquals("test2", postFind.getTitle());
        Assertions.assertEquals(BoardStatus.valueOf("club"), postFind.getBoardName());
    }

    @Test
    @DisplayName("내 게시글 삭제하기")
    @WithAuthUser
    void test4() throws Exception {
        User user = userRepository.findByLoginId("asd@mju.ac.kr").orElseThrow(() -> new UserNotFound());

        Post post= Post.builder()
                .title("test")
                .content("test")
                .boardName(BoardStatus.valueOf("club"))
                .user(user)
                .build();

        postRepository.save(post);

        mockMvc.perform(MockMvcRequestBuilders.delete("/match/my/{postId}",post.getPostId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());

        Assertions.assertEquals(0,postRepository.count());
    }

    @Test
    @DisplayName("내 게시글 매칭 지원 현황(게시글 작성자 입장)")
    @WithAuthUser
    void test5() throws Exception {

        User user = userRepository.findByLoginId("asd@mju.ac.kr").orElseThrow(() -> new UserNotFound());

        Post post= Post.builder()
                .title("test")
                .content("test")
                .boardName(BoardStatus.valueOf("club"))
                .user(user)
                .build();

        postRepository.save(post);

        User userMatch = User.builder()
                .loginId("111")
                .password("222")
                .build();

        userRepository.save(userMatch);

        Applicant applicant = Applicant.builder()
                .user(userMatch)
                .post(post)
                .build();

        applicantRepository.save(applicant);

        mockMvc.perform(MockMvcRequestBuilders.get("/match/{postId}",post.getPostId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("회원 매칭 수락(게시글 작성자 입장)")
    @WithAuthUser
    void test6() throws Exception {
        User user = userRepository.findByLoginId("asd@mju.ac.kr").orElseThrow(() -> new UserNotFound());

        Post post= Post.builder()
                .title("test")
                .content("test")
                .boardName(BoardStatus.valueOf("club"))
                .user(user)
                .build();

        postRepository.save(post);

        User userMatch = User.builder()
                .loginId("111")
                .password("222")
                .build();

        userRepository.save(userMatch);

        Applicant applicant = Applicant.builder()
                .user(userMatch)
                .post(post)
                .build();

        applicantRepository.save(applicant);

        mockMvc.perform(MockMvcRequestBuilders.post("/{postId}/accept/{applicantId}",post.getPostId(),userMatch.getUserId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("회원 매칭 거절(게시글 작성자 입장)")
    @WithAuthUser
    void test7() throws Exception {
        User user = userRepository.findByLoginId("asd@mju.ac.kr").orElseThrow(() -> new UserNotFound());

        Post post= Post.builder()
                .title("test")
                .content("test")
                .boardName(BoardStatus.valueOf("club"))
                .user(user)
                .build();

        postRepository.save(post);

        User userMatch = User.builder()
                .loginId("111")
                .password("222")
                .build();

        userRepository.save(userMatch);

        Applicant applicant = Applicant.builder()
                .user(userMatch)
                .post(post)
                .build();

        applicantRepository.save(applicant);

        mockMvc.perform(MockMvcRequestBuilders.post("/{postId}/refuse/{applicantId}",post.getPostId(),userMatch.getUserId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }
}