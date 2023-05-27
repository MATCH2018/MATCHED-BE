package com.linked.matched.service.notice;

import com.linked.matched.request.notice.NoticeCreate;
import com.linked.matched.request.notice.NoticeEdit;
import com.linked.matched.response.notice.NoticeResponse;

import java.security.Principal;
import java.util.List;

public interface NoticeService {
    List<NoticeResponse> getList();

    NoticeResponse findNotice(Long noticeId);

    void writeNotice(NoticeCreate noticeCreate, Principal principal);

    void editNotice(Long noticeId, NoticeEdit noticeEdit);

    void deleteNotice(Long noticeId);
}
