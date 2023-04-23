package com.linked.matched.controller.notice;

import com.linked.matched.request.notice.NoticeCreate;
import com.linked.matched.request.notice.NoticeEdit;
import com.linked.matched.response.notice.NoticeResponse;
import com.linked.matched.service.notice.NoticeService;
import lombok.RequiredArgsConstructor;
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
    public void createNotice(@RequestBody NoticeCreate noticeCreate){
        noticeService.writeNotice(noticeCreate);
    }

    @GetMapping("/board/notice/{noticeId}")
    public NoticeResponse viewNotice(@PathVariable Long noticeId){
        return noticeService.findNotice(noticeId);
    }

    @PatchMapping("/board/notice/{noticeId}")
    public void editNotice(@PathVariable Long noticeId, @RequestBody NoticeEdit noticeEdit){
        noticeService.editNotice(noticeId,noticeEdit);
    }

    @DeleteMapping("/board/notice/{noticeId}")
    public void deleteNotice(@PathVariable Long noticeId){
        noticeService.deleteNotice(noticeId);
    }

}
