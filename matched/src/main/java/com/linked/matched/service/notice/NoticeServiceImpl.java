package com.linked.matched.service.notice;

import com.linked.matched.entity.Notice;
import com.linked.matched.entity.User;
import com.linked.matched.exception.PostNotFound;
import com.linked.matched.exception.UserNotFound;
import com.linked.matched.repository.notice.NoticeRepository;
import com.linked.matched.repository.user.UserRepository;
import com.linked.matched.request.notice.NoticeCreate;
import com.linked.matched.request.notice.NoticeEdit;
import com.linked.matched.response.notice.NoticeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoticeServiceImpl implements NoticeService{

    private final NoticeRepository noticeRepository;
    private final UserRepository userRepository;

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
                .orElseThrow(PostNotFound::new);
    }

    @Override
    public void writeNotice(NoticeCreate noticeCreate, Principal principal){

        User user = userRepository.findById(Long.valueOf(principal.getName())).orElseThrow(() -> new UserNotFound());

        Notice notice=Notice.builder()
                .title(noticeCreate.getTitle())
                .content(noticeCreate.getContent())
                .user(user)
                .build();

        noticeRepository.save(notice);
    }

    @Override
    @Transactional
    public void editNotice(Long noticeId, NoticeEdit noticeEdit){

        Notice notice = noticeRepository.findById(noticeId).orElseThrow(PostNotFound::new);

        notice.edit(noticeEdit);
    }

    @Override
    public void deleteNotice(Long noticeId) {

        Notice notice = noticeRepository.findById(noticeId).orElseThrow(PostNotFound::new);

        noticeRepository.delete(notice);
    }

}
