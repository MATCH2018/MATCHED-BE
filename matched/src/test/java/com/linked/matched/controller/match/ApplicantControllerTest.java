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
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ApplicantControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ApplicantRepository applicantRepository;

    @AfterEach
    void clean(){
        userRepository.deleteAll();
        applicantRepository.deleteAll();
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("매칭 지원")
    @WithAuthUser
    void test1() throws Exception {

        User userMatch = User.builder()
                .loginId("1234")
                .password("1234")
                .build();

        userRepository.save(userMatch);

        Post post= Post.builder()
                .title("test")
                .content("test")
                .boardName(BoardStatus.valueOf("club"))
                .user(userMatch)
                .build();

        postRepository.save(post);

        mockMvc.perform(MockMvcRequestBuilders.post("/match/{postId}",post.getPostId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());

        Assertions.assertEquals(applicantRepository.count(), 1);
    }

    @Test
    @DisplayName("매칭 지원 취소")
    @WithAuthUser
    void test2() throws Exception {
        User user = userRepository.findByLoginId("asd@mju.ac.kr").orElseThrow(() -> new UserNotFound());

        User userMatch = User.builder()
                .loginId("1234")
                .password("1234")
                .build();

        userRepository.save(userMatch);

        Post post= Post.builder()
                .title("test")
                .content("test")
                .boardName(BoardStatus.valueOf("club"))
                .user(userMatch)
                .build();

        postRepository.save(post);

        Applicant applicant = Applicant.builder()
                .user(user)
                .post(post)
                .build();
        applicantRepository.save(applicant);

        mockMvc.perform(MockMvcRequestBuilders.delete("/match/{postId}",post.getPostId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());

        Assertions.assertEquals(applicantRepository.count(), 0);

    }

    @Test
    @DisplayName("지원자가 매칭 지원한 게시글 목록 조회")
    @WithAuthUser
    void test3() throws Exception {
        User user = userRepository.findByLoginId("asd@mju.ac.kr").orElseThrow(() -> new UserNotFound());

        User userMatch = User.builder()
                .loginId("1234")
                .password("1234")
                .build();

        userRepository.save(userMatch);

        Post post= Post.builder()
                .title("test")
                .content("test")
                .boardName(BoardStatus.valueOf("club"))
                .user(userMatch)
                .build();

        postRepository.save(post);

        Applicant applicant = Applicant.builder()
                .user(user)
                .post(post)
                .build();
        applicantRepository.save(applicant);

        Post post2= Post.builder()
                .title("test")
                .content("test")
                .boardName(BoardStatus.valueOf("club"))
                .user(userMatch)
                .build();

        postRepository.save(post2);

        Applicant applicant2 = Applicant.builder()
                .user(user)
                .post(post2)
                .build();
        applicantRepository.save(applicant2);

        mockMvc.perform(MockMvcRequestBuilders.get("/match/applicant",post.getPostId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    @DisplayName("자신의 게시글은 매칭 지원 불가능")
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

        mockMvc.perform(MockMvcRequestBuilders.post("/match/{postId}",post.getPostId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(MockMvcResultHandlers.print());
    }

}