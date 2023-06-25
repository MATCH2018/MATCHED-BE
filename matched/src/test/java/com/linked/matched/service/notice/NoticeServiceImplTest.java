package com.linked.matched.service.notice;

import com.linked.matched.entity.Notice;
import com.linked.matched.entity.User;
import com.linked.matched.repository.notice.NoticeRepository;
import com.linked.matched.repository.user.UserRepository;
import com.linked.matched.request.notice.NoticeCreate;
import com.linked.matched.request.notice.NoticeEdit;
import com.linked.matched.response.notice.NoticeResponse;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;


@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class NoticeServiceImplTest {

    @Autowired
    private NoticeService noticeService;

    @Autowired
    private NoticeRepository noticeRepository;

    @Autowired
    private UserRepository userRepository;

    @AfterEach
    void clean(){
        noticeRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("공지 작성")
    void test(){

        NoticeCreate noticeCreate = NoticeCreate.builder()
                .title("제목입니다")
                .content("내용입니다")
                .build();

//        noticeService.writeNotice(noticeCreate);
//
//        Assertions.assertEquals(noticeRepository.count(),1);
//
//        Notice notice = noticeRepository.findAll().iterator().next();
//
//        Assertions.assertEquals(notice.getTitle(),"제목입니다");
//        Assertions.assertEquals(notice.getContent(),"내용입니다");
    }

    @Test
    @DisplayName("공지 1개 조회")
    void test2(){
        Notice notice1 = Notice.builder()
                .title("제목1")
                .content("내용1")
                .build();

        Notice notice2 = Notice.builder()
                .title("제목2")
                .content("내용2")
                .build();

        noticeRepository.save(notice1);
        noticeRepository.save(notice2);

        NoticeResponse notice = noticeService.findNotice(notice2.getNoticeId());

        Assertions.assertEquals(notice.getTitle(),"제목2");
        Assertions.assertEquals(notice.getContent(),"내용2");
    }

    @Test
    @DisplayName("공지 삭제")
    void test3(){
        Notice notice = Notice.builder()
                .title("제목1")
                .content("내용1")
                .build();
        noticeRepository.save(notice);

        noticeService.deleteNotice(notice.getNoticeId());

        Assertions.assertEquals(noticeRepository.count(),0);
    }

    @Test
    @DisplayName("공지 수정")
    void test4(){
        Notice notice = Notice.builder()
                .title("제목1")
                .content("내용1")
                .build();
        noticeRepository.save(notice);

        NoticeEdit noticeEdit = NoticeEdit.builder()
                .title("제목2")
                .content("내용2")
                .build();

        noticeService.editNotice(notice.getNoticeId(),noticeEdit);

        Notice next = noticeRepository.findAll().iterator().next();

        Assertions.assertEquals(noticeRepository.count(),1);
        Assertions.assertEquals(next.getTitle(),"제목2");
        Assertions.assertEquals(next.getContent(),"내용2");

    }
    
    @Test
    @DisplayName("조회 글 전체 조회")
    void test5(){
        Notice notice1 = Notice.builder()
                .title("제목1")
                .content("내용1")
                .build();
        noticeRepository.save(notice1);

        Notice notice2 = Notice.builder()
                .title("제목2")
                .content("내용2")
                .build();
        noticeRepository.save(notice2);

        Notice notice3 = Notice.builder()
                .title("제목3")
                .content("내용3")
                .build();
        noticeRepository.save(notice3);

        List<NoticeResponse> noticeList = noticeService.getList();

        Assertions.assertEquals(noticeList.size(),3);
        Assertions.assertEquals(noticeList.get(0).getTitle(),"제목1");
        Assertions.assertEquals(noticeList.get(1).getTitle(),"제목2");
        Assertions.assertEquals(noticeList.get(2).getTitle(),"제목3");
    }
}