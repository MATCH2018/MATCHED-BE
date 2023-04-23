package com.linked.matched.service.notice;

import com.linked.matched.entity.Notice;
import com.linked.matched.repository.notice.NoticeRepository;
import com.linked.matched.request.notice.NoticeCreate;
import com.linked.matched.request.notice.NoticeEdit;
import com.linked.matched.response.notice.NoticeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoticeServiceImpl implements NoticeService{

    private final NoticeRepository noticeRepository;

    @Override
    public List<NoticeResponse> getList(){
        return noticeRepository.findAll().stream()
                .map(NoticeResponse::new)
                .collect(Collectors.toList());
    }

    @Override
    public NoticeResponse findNotice(Long noticeId) {
        return noticeRepository.findById(noticeId)
                .map(NoticeResponse::new)
                .orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public void writeNotice(NoticeCreate noticeCreate){
        Notice notice=Notice.builder()
                .title(noticeCreate.getTitle())
                .content(noticeCreate.getContent())
                .build();

        noticeRepository.save(notice);
    }

    @Override
    @Transactional
    public void editNotice(Long noticeId, NoticeEdit noticeEdit){

        Notice notice = noticeRepository.findById(noticeId).orElseThrow(() -> new IllegalArgumentException());

        notice.edit(noticeEdit);
    }

    @Override
    public void deleteNotice(Long noticeId) {
        noticeRepository.deleteById(noticeId);
    }

}
