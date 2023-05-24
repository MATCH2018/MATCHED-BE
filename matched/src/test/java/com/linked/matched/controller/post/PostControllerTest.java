package com.linked.matched.controller.post;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.linked.matched.entity.Post;
import com.linked.matched.entity.User;
import com.linked.matched.entity.status.BoardStatus;
import com.linked.matched.repository.post.PostRepository;
import com.linked.matched.repository.user.UserRepository;
import com.linked.matched.request.post.PostCreate;
import com.linked.matched.request.post.PostEdit;
import com.linked.matched.request.post.PostSearch;
import org.junit.jupiter.api.*;
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
class PostControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @AfterEach
    void clean() {
        postRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("글 목록 조회")
    void test1() throws Exception {

        Post request = Post.builder()
                .postId(1L)
                .title("제목입니다1.")
                .content("내용입니다1.")
                .limitPeople(3)
                .boardName(BoardStatus.valueOf("club"))
                .build();

        postRepository.save(request);

        Post request2 = Post.builder()
                .postId(2L)
                .title("제목입니다2.")
                .content("내용입니다2.")
                .limitPeople(3)
                .boardName(BoardStatus.valueOf("poom"))
                .build();

        postRepository.save(request2);

        Post request3 = Post.builder()
                .postId(3L)
                .title("제목입니다3.")
                .content("내용입니다3.")
                .limitPeople(3)
                .boardName(BoardStatus.valueOf("club"))
                .build();

        postRepository.save(request3);


        mockMvc.perform(MockMvcRequestBuilders.get("/board/{boardName}?page=1&size=10", "club")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("제목입니다3."))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].title").value("제목입니다1."))
                .andDo(MockMvcResultHandlers.print());
    }

  //  @Test
    @DisplayName("글 저장")
    void test2() throws Exception {



        PostCreate request = PostCreate.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .limitPeople(3)
                .boardName(BoardStatus.valueOf("club"))
                .build();



        String json = objectMapper.writeValueAsString(request);

        // when
        mockMvc.perform(MockMvcRequestBuilders.post("/board/{boardName}", request.getBoardName())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());

        Post next = postRepository.findAll().iterator().next();

        //then
        Assertions.assertEquals(1L, postRepository.count());
        Assertions.assertEquals(next.getTitle(), "제목입니다.");
    }

  //  @Test
    @DisplayName("글 수정")
    void test3() throws Exception {
        Post request = Post.builder()
                .postId(1L)
                .title("제목입니다1.")
                .content("내용입니다1.")
                .limitPeople(3)
                .boardName(BoardStatus.valueOf("club"))
                .build();

        postRepository.save(request);

        PostEdit requestEdit = PostEdit.builder()
                .title("제목입니다2.")
                .content("내용입니다2.")
                .limitPeople(4)
                .boardName(BoardStatus.valueOf("club"))
                .build();

        mockMvc.perform(MockMvcRequestBuilders.patch("/board/{boardName}/{postId}", request.getBoardName(), request.getPostId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestEdit)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());

        Assertions.assertEquals(postRepository.findById(1L).get().getTitle(), "제목입니다2.");

    }

 //   @Test
    @DisplayName("글 삭제")
    void test4() throws Exception {
        Post request = Post.builder()
                .postId(1L)
                .title("제목입니다1.")
                .content("내용입니다1.")
                .limitPeople(3)
                .boardName(BoardStatus.valueOf("club"))
                .build();

        postRepository.save(request);

        Assertions.assertEquals(1,postRepository.count());

        mockMvc.perform(MockMvcRequestBuilders.delete("/board/{boardName}/{postId}", request.getBoardName(), request.getPostId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());

        Assertions.assertEquals(postRepository.count(),0);
    }
    @Test
    @DisplayName("글 1개 조회")
    void test5() throws Exception {
        Post request = Post.builder()
                .postId(1L)
                .title("제목입니다1.")
                .content("내용입니다1.")
                .limitPeople(3)
                .boardName(BoardStatus.valueOf("club"))
                .build();

        postRepository.save(request);

        Post request2 = Post.builder()
                .postId(2L)
                .title("제목입니다2.")
                .content("내용입니다2.")
                .limitPeople(3)
                .boardName(BoardStatus.valueOf("club"))
                .build();

        postRepository.save(request2);


        mockMvc.perform(MockMvcRequestBuilders.get("/board/{boardName}/{postId}",request.getBoardName(),request.getPostId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("제목입니다1."))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").value("내용입니다1."))
                .andDo(MockMvcResultHandlers.print());

    }
}