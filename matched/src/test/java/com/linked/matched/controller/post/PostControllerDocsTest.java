package com.linked.matched.controller.post;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.linked.matched.entity.Post;
import com.linked.matched.entity.status.BoardStatus;
import com.linked.matched.repository.post.PostRepository;
import com.linked.matched.request.post.PostCreate;
import com.linked.matched.request.post.PostEdit;
import com.linked.matched.request.post.PostSearch;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.restdocs.request.RequestDocumentation;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;



import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;


@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs(uriScheme = "https", uriHost = "matched.com", uriPort = 443)
@ExtendWith(RestDocumentationExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class PostControllerDocsTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @AfterEach
    void clean() {
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("글 단건 조회")
    void test() throws Exception {
        Post request = Post.builder()
                .postId(1L)
                .title("제목입니다1.")
                .content("내용입니다1.")
                .limitPeople(3)
                .boardName(BoardStatus.valueOf("club"))
                .build();

        postRepository.save(request);

        Assertions.assertEquals(postRepository.count(),1);

        ResultActions resultActions = this.mockMvc.perform(RestDocumentationRequestBuilders.get("/board/{boardName}/{postId}", request.getBoardName(), request.getPostId())
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());

        resultActions.andDo(document("post-inquiry",
                        RequestDocumentation.pathParameters(
                                RequestDocumentation.parameterWithName("postId").description("게시글 ID"),
                                RequestDocumentation.parameterWithName("boardName").description("club")
                        ), PayloadDocumentation.responseFields(
                                PayloadDocumentation.fieldWithPath("postId").description("게시글 ID"),
                                PayloadDocumentation.fieldWithPath("title").description("제목"),
                                PayloadDocumentation.fieldWithPath("content").description("내용"),
                                PayloadDocumentation.fieldWithPath("limitPeople").description("인원제한 수"),
                                PayloadDocumentation.fieldWithPath("createdAt").description("만든 시간"),
                                PayloadDocumentation.fieldWithPath("updateAt").description("업데이트 시간"),
                                PayloadDocumentation.fieldWithPath("boardName").description("검색 종류-ex)club,capstone,poom,tutoring")
                        )
                ));

    }

    @Test
    @DisplayName("글 저장하기")
    void test2() throws Exception {
        PostCreate request = PostCreate.builder()
                .title("제목입니다1.")
                .content("내용입니다1.")
                .limitPeople(3)
                .categoryName("팀원")
                .boardName(BoardStatus.valueOf("club"))
                .build();

        String json = objectMapper.writeValueAsString(request);

        this.mockMvc.perform(RestDocumentationRequestBuilders.post("/board/{boardName}",request.getBoardName())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(document("post_create",
                        PayloadDocumentation.requestFields(
                                PayloadDocumentation.fieldWithPath("title").type(JsonFieldType.STRING).description("제목입니다."),
                                PayloadDocumentation.fieldWithPath("content").type(JsonFieldType.STRING).description("내용입니다."),
                                PayloadDocumentation.fieldWithPath("createdAt").type(JsonFieldType.STRING).description("생성시간입니다."),
                                PayloadDocumentation.fieldWithPath("limitPeople").type(JsonFieldType.NUMBER).description(3),
                                PayloadDocumentation.fieldWithPath("boardName").type(JsonFieldType.STRING).description("club,capstone,poom,tutoring"),
                                PayloadDocumentation.fieldWithPath("categoryName").type(JsonFieldType.STRING).description("카테고리 종류")
                        ))
                );
    }
    
    @Test
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

        this.mockMvc.perform(RestDocumentationRequestBuilders.patch("/board/{boardName}/{postId}",request.getBoardName(), request.getPostId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestEdit)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(document("post_edit",
                        PayloadDocumentation.requestFields(
                                PayloadDocumentation.fieldWithPath("title").type(JsonFieldType.STRING).description("제목입니다."),
                                PayloadDocumentation.fieldWithPath("content").type(JsonFieldType.STRING).description("내용입니다."),
                                PayloadDocumentation.fieldWithPath("updateAt").type(JsonFieldType.STRING).description("업데이트 시간입니다."),
                                PayloadDocumentation.fieldWithPath("limitPeople").type(JsonFieldType.STRING).description("인원 수 입니다."),
                                PayloadDocumentation.fieldWithPath("boardName").type(JsonFieldType.STRING).description("club,capstone,poom,tutoring"),
                                PayloadDocumentation.fieldWithPath("categoryName").type(JsonFieldType.STRING).description("카테고리 종류")
                        ))
                );
    }

    @Test
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

        this.mockMvc.perform(RestDocumentationRequestBuilders.delete("/board/{boardName}/{postId}",request.getBoardName(), request.getPostId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request.getPostId())))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(document("post_delete",
                                RequestDocumentation.pathParameters(
                                        RequestDocumentation.parameterWithName("postId").description("게시글 ID"),
                                        RequestDocumentation.parameterWithName("boardName").description("club")
                        ))
                );
    }

    @Test
    @DisplayName("글 목록 조회")
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

        PostSearch page = PostSearch.builder()
                .page(1)
                .build();

        String json = objectMapper.writeValueAsString(page);

        mockMvc.perform(RestDocumentationRequestBuilders.get("/board/{boardName}",request.getBoardName())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("제목입니다3."))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].content").value("내용입니다1."))
                .andDo(MockMvcResultHandlers.print())
                .andDo(document("post-find-category",
                            RequestDocumentation.pathParameters(
                                    RequestDocumentation.parameterWithName("boardName").description("club")
                            ), PayloadDocumentation.responseFields(
                                    PayloadDocumentation.fieldWithPath("[].postId").description("게시글입니다."),
                                    PayloadDocumentation.fieldWithPath("[].title").description("제목"),
                                    PayloadDocumentation.fieldWithPath("[].content").description("내용"),
                                    PayloadDocumentation.fieldWithPath("[].limitPeople").description("인원제한 수"),
                                    PayloadDocumentation.fieldWithPath("[].createdAt").description("만든 시간"),
                                    PayloadDocumentation.fieldWithPath("[].updateAt").description("업데이트 시간"),
                                    PayloadDocumentation.fieldWithPath("[].boardName").description("검색 종류-ex)club,capstone,poom,tutoring")
                        )
                ));

    }

}
