package com.linked.matched.service.notice;

import com.linked.matched.entity.Notice;
import com.linked.matched.request.notice.NoticeEditor;
import com.linked.matched.entity.User;
import com.linked.matched.exception.post.PostNotFound;
import com.linked.matched.exception.user.UserNotFound;
import com.linked.matched.repository.notice.NoticeRepository;
import com.linked.matched.repository.user.UserRepository;
import com.linked.matched.request.notice.NoticeCreate;
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
    public void writeNotice(NoticeCreate noticeCreate, Long id){

        User user = userRepository.findById(Long.valueOf(id)).orElseThrow(() -> new UserNotFound());

        Notice notice=Notice.builder()
                .title(noticeCreate.getTitle())
                .content(noticeCreate.getContent())
                .user(user)
                .build();

        noticeRepository.save(notice);
    }

    @Override
    @Transactional
    public void editNotice(Long noticeId, NoticeEditor noticeEdit){

        Notice notice = noticeRepository.findById(noticeId).orElseThrow(PostNotFound::new);

        NoticeEditor.NoticeEditorBuilder noticeEditorBuilder = notice.toEditor();

        if(noticeEdit.getTitle()!=null){
            noticeEditorBuilder.title(noticeEdit.getTitle());
        }

        if(noticeEdit.getContent()!=null){
            noticeEditorBuilder.content(noticeEdit.getContent());
        }

        notice.edit(noticeEditorBuilder.build());
    }

    @Override
    public void deleteNotice(Long noticeId) {

        Notice notice = noticeRepository.findById(noticeId).orElseThrow(PostNotFound::new);

        noticeRepository.delete(notice);
    }

}
