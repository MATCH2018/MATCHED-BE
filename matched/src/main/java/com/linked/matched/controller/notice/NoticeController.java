package com.linked.matched.controller.notice;

import com.linked.matched.request.notice.NoticeCreate;
import com.linked.matched.request.notice.NoticeEdit;
import com.linked.matched.response.ResponseDto;
import com.linked.matched.response.notice.NoticeResponse;
import com.linked.matched.service.notice.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;

    @GetMapping("/board/notice") //공지 글 목록 조회
    public List<NoticeResponse> viewListNotice(){
        return noticeService.getList();
    }

    @PostMapping("/board/notice") // 공지 글 작성
    @PreAuthorize("hasAnyRole('ADMIN')") 
    public ResponseEntity<Object> createNotice(@RequestBody NoticeCreate noticeCreate, Principal principal){// 공지 작성이 되었습니다.
        noticeService.writeNotice(noticeCreate,principal);
        return new ResponseEntity<>(new ResponseDto("공지 작성이 되었습니다."), HttpStatus.OK);
    }

    @GetMapping("/board/notice/{noticeId}") // 공지 글 자세히 보기
    public NoticeResponse viewNotice(@PathVariable Long noticeId){
        return noticeService.findNotice(noticeId);
    }

    @PatchMapping("/board/notice/{noticeId}") // 공지 글 수정
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Object> editNotice(@PathVariable Long noticeId, @RequestBody NoticeEdit noticeEdit){// 공지 수정 되었습니다.
        noticeService.editNotice(noticeId,noticeEdit);
        return new ResponseEntity<>(new ResponseDto("공지 수정 되었습니다."), HttpStatus.OK);
    }

    @DeleteMapping("/board/notice/{noticeId}") // 공지 글 삭제
    @PreAuthorize("hasAnyRole('ADMIN')") 
    public ResponseEntity<Object> deleteNotice(@PathVariable Long noticeId){
        noticeService.deleteNotice(noticeId);
        return new ResponseEntity<>(new ResponseDto("공지 삭제 되었습니다."), HttpStatus.OK);
    }

}
