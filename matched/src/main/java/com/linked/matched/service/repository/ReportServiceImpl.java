package com.linked.matched.service.repository;

import com.linked.matched.entity.Post;
import com.linked.matched.entity.PostReport;
import com.linked.matched.entity.User;
import com.linked.matched.exception.report.AlreadyReport;
import com.linked.matched.exception.report.NotSelfReport;
import com.linked.matched.exception.post.PostNotFound;
import com.linked.matched.exception.user.UserNotFound;
import com.linked.matched.repository.post.PostRepository;
import com.linked.matched.repository.report.PostReportRepository;
import com.linked.matched.repository.user.UserRepository;
import com.linked.matched.request.report.PostReportRequest;
import com.linked.matched.service.repository.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ReportServiceImpl implements ReportService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final PostReportRepository postReportRepository;

    @Override
    @Transactional
    public void reportBoard(PostReportRequest req) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User reporter = userRepository.findByName(authentication.getName()).orElseThrow(UserNotFound::new);
        Post reportedPost = postRepository.findById(req.getReportedPostId()).orElseThrow(PostNotFound::new);

        if(reporter.getUserId().equals(reportedPost.getUser().getUserId())) {
            throw new NotSelfReport();
        }

        if(postReportRepository.findByPost(reportedPost) == null) {
            // 신고 한 적이 없다면, 테이블 생성 후 신고 처리
            PostReport postReport = PostReport.builder()
                    .reporterId(reporter.getUserId())
                    .post(reportedPost)
                    .content(req.getContent())
                    .build();
            postReportRepository.save(postReport);

            if(postReportRepository.findByPost(reportedPost).size() >= 10) {
                // 신고 수 10 이상일 시 true 설정
                reportedPost.makeStatusReported();
            }

        } else {
            throw new AlreadyReport();
        }
    }

}
