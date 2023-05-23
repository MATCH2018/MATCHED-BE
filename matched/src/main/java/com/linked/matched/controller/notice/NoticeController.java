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

import java.util.List;

@RestController
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;

    @GetMapping("/board/notice")
    public List<NoticeResponse> viewListNotice(){
        return noticeService.getList();
    }

    @PostMapping("/board/notice")
//    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Object> createNotice(@RequestBody NoticeCreate noticeCreate){// 공지 작성이 되었습니다.
        noticeService.writeNotice(noticeCreate);
        return new ResponseEntity<>(new ResponseDto("공지 작성이 되었습니다."), HttpStatus.OK);
    }

    @GetMapping("/board/notice/{noticeId}")
    public NoticeResponse viewNotice(@PathVariable Long noticeId){
        return noticeService.findNotice(noticeId);
    }

    @PatchMapping("/board/notice/{noticeId}")
//    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Object> editNotice(@PathVariable Long noticeId, @RequestBody NoticeEdit noticeEdit){// 공지 수정 되었습니다.
        noticeService.editNotice(noticeId,noticeEdit);
        return new ResponseEntity<>(new ResponseDto("공지 수정 되었습니다."), HttpStatus.OK);
    }

    @DeleteMapping("/board/notice/{noticeId}")
//    @PreAuthorize("hasAnyRole('ADMIN')") //공지가 삭제 되었습니다.
    public ResponseEntity<Object> deleteNotice(@PathVariable Long noticeId){
        noticeService.deleteNotice(noticeId);
        return new ResponseEntity<>(new ResponseDto("공지 삭제 되었습니다."), HttpStatus.OK);
    }

}
