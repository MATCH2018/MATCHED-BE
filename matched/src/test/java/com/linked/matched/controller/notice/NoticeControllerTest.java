package com.linked.matched.controller.notice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.linked.matched.annotation.WithAuthUser;
import com.linked.matched.entity.Notice;
import com.linked.matched.repository.notice.NoticeRepository;
import com.linked.matched.request.notice.NoticeCreate;
import com.linked.matched.request.notice.NoticeEditor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class NoticeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private NoticeRepository noticeRepository;

    @AfterEach
    void clean(){
        noticeRepository.deleteAll();
    }


    @Test
    @DisplayName("공지 생성")
    @WithAuthUser(authorityName="ROLE_ADMIN")
    void test1() throws Exception {

        NoticeCreate notice = NoticeCreate.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .build();

        String json = objectMapper.writeValueAsString(notice);

        mockMvc.perform(MockMvcRequestBuilders.post("/board/notice")
                        .with(SecurityMockMvcRequestPostProcessors.csrf().asHeader())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());

        Notice next = noticeRepository.findAll().iterator().next();

        Assertions.assertEquals(noticeRepository.count(),1L);
        Assertions.assertEquals(next.getTitle(),"제목입니다.");
    }

    @Test
    @DisplayName("공지 수정")
    @WithAuthUser(authorityName="ROLE_ADMIN")
    void test2() throws Exception{
        Notice notice = Notice.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .build();

        noticeRepository.save(notice);

        NoticeEditor noticeEdit = NoticeEditor.builder()
                .title("제목")
                .content("내용")
                .build();

        mockMvc.perform(MockMvcRequestBuilders.patch("/board/notice/{noticeId}",notice.getNoticeId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(noticeEdit)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());

        Notice next = noticeRepository.findAll().iterator().next();

        Assertions.assertEquals(next.getTitle(), "제목");

    }
    
    @Test
    @DisplayName("글 삭제")
    @WithAuthUser(authorityName="ROLE_ADMIN")
    void test3() throws Exception{
        Notice notice = Notice.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .build();

        noticeRepository.save(notice);

        Assertions.assertEquals(noticeRepository.count(),1);

        mockMvc.perform(MockMvcRequestBuilders.delete("/board/notice/{noticeId}", notice.getNoticeId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
        Assertions.assertEquals(noticeRepository.count(),0);
    }
    
    @Test
    @DisplayName("글 1개 조회")
    @WithAuthUser
    void test4() throws Exception{
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

        mockMvc.perform(MockMvcRequestBuilders.get("/board/notice/{noticeId}", notice2.getNoticeId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("제목2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").value("내용2"))
                .andDo(MockMvcResultHandlers.print());
    }
    
    @Test
    @DisplayName("공지 목록 조회")
    @WithAuthUser
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

        mockMvc.perform(MockMvcRequestBuilders.get("/board/notice")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("제목입니다."))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].title").value("제목입니다3."))
                .andExpect(MockMvcResultMatchers.jsonPath("$[3].content").value("내용4"))
                .andDo(MockMvcResultHandlers.print());
    }
    
    @Test
    @DisplayName("ROLE_USER 권한으로 글 작성 못 한다.")
    @WithAuthUser
    void test6() throws Exception {

        NoticeCreate notice = NoticeCreate.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .build();

        String json = objectMapper.writeValueAsString(notice);

        mockMvc.perform(MockMvcRequestBuilders.post("/board/notice")
                        .with(SecurityMockMvcRequestPostProcessors.csrf().asHeader())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().isForbidden())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("ROLE_USER 권한으로 글 수정 못한다.")
    @WithAuthUser
    void test7()throws Exception{
        Notice notice = Notice.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .build();

        noticeRepository.save(notice);

        NoticeEditor noticeEdit = NoticeEditor.builder()
                .title("제목")
                .content("내용")
                .build();

        mockMvc.perform(MockMvcRequestBuilders.patch("/board/notice/{noticeId}",notice.getNoticeId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(noticeEdit)))
                .andExpect(MockMvcResultMatchers.status().isForbidden())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("ROLE_USER 권한으로 글 삭제 못한다.")
    @WithAuthUser
    void test8()throws Exception{
        Notice notice = Notice.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .build();

        noticeRepository.save(notice);

        Assertions.assertEquals(noticeRepository.count(),1);

        mockMvc.perform(MockMvcRequestBuilders.delete("/board/notice/{noticeId}", notice.getNoticeId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isForbidden())
                .andDo(MockMvcResultHandlers.print());
    }
}