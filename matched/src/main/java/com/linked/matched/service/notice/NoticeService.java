package com.linked.matched.service.notice;

import com.linked.matched.request.notice.NoticeCreate;
import com.linked.matched.request.notice.NoticeEditor;
import com.linked.matched.response.notice.NoticeResponse;

import java.util.List;

public interface NoticeService {
    List<NoticeResponse> getList();

    NoticeResponse findNotice(Long noticeId);

    void writeNotice(NoticeCreate noticeCreate,Long id);

    void editNotice(Long noticeId, NoticeEditor noticeEdit);

    void deleteNotice(Long noticeId);
}
