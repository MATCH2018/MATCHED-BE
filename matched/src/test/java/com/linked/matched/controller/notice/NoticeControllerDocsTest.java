package com.linked.matched.controller.notice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.linked.matched.entity.Notice;
import com.linked.matched.entity.User;
import com.linked.matched.repository.notice.NoticeRepository;
import com.linked.matched.repository.post.PostRepository;
import com.linked.matched.repository.user.UserRepository;
import com.linked.matched.request.notice.NoticeCreate;
import com.linked.matched.request.notice.NoticeEdit;
import org.junit.jupiter.api.BeforeEach;
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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs(uriScheme = "https", uriHost = "linked.matched.com", uriPort = 443)
@ExtendWith(RestDocumentationExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class NoticeControllerDocsTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private NoticeRepository noticeRepository;

    @Autowired
    private ObjectMapper objectMapper;


    @BeforeEach
    void clean(){
        noticeRepository.deleteAll();
    }

    //@Test
    @DisplayName("공지 저장")
    @WithMockUser
    void test1() throws Exception {

        NoticeCreate notice = NoticeCreate.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .build();

        String json = objectMapper.writeValueAsString(notice);

        this.mockMvc.perform(RestDocumentationRequestBuilders.post("/board/notice")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(document("notice_create",
                        PayloadDocumentation.requestFields(
                                PayloadDocumentation.fieldWithPath("title").type(JsonFieldType.STRING).description("제목입니다."),
                                PayloadDocumentation.fieldWithPath("content").type(JsonFieldType.STRING).description("내용입니다."),
                                PayloadDocumentation.fieldWithPath("createdAt").description("생성시간입니다.")
                        ))
                );
    }

    @Test
    @DisplayName("공지 수정")
    void test2() throws Exception {
        Notice notice = Notice.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .build();

        noticeRepository.save(notice);

        NoticeEdit noticeEdit = NoticeEdit.builder()
                .title("제목")
                .content("내용")
                .build();

        this.mockMvc.perform(RestDocumentationRequestBuilders.patch("/board/notice/{noticeId}",notice.getNoticeId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(noticeEdit)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(document("notice_edit",
                        PayloadDocumentation.requestFields(
                                PayloadDocumentation.fieldWithPath("title").type(JsonFieldType.STRING).description("제목입니다."),
                                PayloadDocumentation.fieldWithPath("content").type(JsonFieldType.STRING).description("내용입니다.")
                        ))
                );
    }

    @Test
    @DisplayName("글 삭제")
    void test3() throws Exception {
        Notice notice = Notice.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .build();
        noticeRepository.save(notice);

        this.mockMvc.perform(RestDocumentationRequestBuilders.delete("/board/notice/{noticeId}",notice.getNoticeId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(notice.getNoticeId())))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(document("notice_delete",
                        RequestDocumentation.pathParameters(
                                RequestDocumentation.parameterWithName("noticeId").description("공지글 ID")
                        ))
                );
    }

    @Test
    @DisplayName("글 1개 조회")
    void test4() throws Exception {
        Notice notice = Notice.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .build();

        noticeRepository.save(notice);

        Notice notice2 = Notice.builder()
                .title("제목2")
                .content("내용2")
                .build();

        noticeRepository.save(notice2);

        this.mockMvc.perform(RestDocumentationRequestBuilders.get("/board/notice/{noticeId}", notice2.getNoticeId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(document("notice_find_one",
                        RequestDocumentation.pathParameters(
                                RequestDocumentation.parameterWithName("noticeId").description("게시글 ID")
                        ), PayloadDocumentation.responseFields(
                                PayloadDocumentation.fieldWithPath("noticeId").description("게시글 ID"),
                                PayloadDocumentation.fieldWithPath("title").description("제목"),
                                PayloadDocumentation.fieldWithPath("content").description("내용"),
                                PayloadDocumentation.fieldWithPath("createdAt").description("날짜입니다.")
                                )
                ));
    }
    @Test
    @DisplayName("공지 목록 조회")
    void test5() throws Exception {
        Notice notice = Notice.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .build();

        noticeRepository.save(notice);

        Notice notice2 = Notice.builder()
                .title("제목2")
                .content("내용2")
                .build();
        noticeRepository.save(notice2);

        Notice notice3 = Notice.builder()
                .title("제목입니다3.")
                .content("내용입니다3.")
                .build();

        noticeRepository.save(notice3);

        Notice notice4 = Notice.builder()
                .title("제목4")
                .content("내용4")
                .build();


        noticeRepository.save(notice4);

        mockMvc.perform(RestDocumentationRequestBuilders.get("/board/notice")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("제목입니다."))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].content").value("내용2"))
                .andDo(MockMvcResultHandlers.print())
                .andDo(document("notice-find",
                         PayloadDocumentation.responseFields(
                                PayloadDocumentation.fieldWithPath("[].noticeId").description("게시글입니다."),
                                PayloadDocumentation.fieldWithPath("[].title").description("제목"),
                                PayloadDocumentation.fieldWithPath("[].content").description("내용"),
                                PayloadDocumentation.fieldWithPath("[].createdAt").description("날짜입니다.")
                         )
                ));
    }
}
