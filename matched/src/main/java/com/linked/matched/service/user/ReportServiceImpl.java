package com.linked.matched.service.user;

import com.linked.matched.entity.Post;
import com.linked.matched.entity.PostReport;
import com.linked.matched.entity.User;
import com.linked.matched.entity.UserReport;
import com.linked.matched.exception.AlreadyReport;
import com.linked.matched.exception.NotSelfReport;
import com.linked.matched.exception.PostNotFound;
import com.linked.matched.exception.UserNotFound;
import com.linked.matched.repository.post.PostRepository;
import com.linked.matched.repository.report.PostReportRepository;
import com.linked.matched.repository.report.UserReportRepository;
import com.linked.matched.repository.user.UserRepository;
import com.linked.matched.request.report.PostReportRequest;
import com.linked.matched.request.report.UserReportRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ReportServiceImpl implements ReportService{

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final UserReportRepository userReportRepository;
    private final PostReportRepository postReportRepository;

    @Override
    @Transactional
    public void reportUser(UserReportRequest req){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User reporter = userRepository.findByName(authentication.getName()).orElseThrow(UserNotFound::new);
        User reportedUser = userRepository.findById(req.getReportUserId()).orElseThrow(UserNotFound::new);

        if(reporter.getUserId().equals(req.getReportUserId())) {
            // 자기 자신을 신고한 경우 + 예외 만들어줘야한다.
            throw new NotSelfReport();
        }

        if(userReportRepository.findByReporterUserId(req.getReportUserId()) == null) {
            // 신고 한 적이 없다면, 테이블 생성 후 신고 처리 (ReportedUser의 User테이블 boolean 값 true 변경 ==> 신고처리)
            UserReport userReport = UserReport.builder()
                    .reporterUserId(reportedUser.getUserId()) // 신고 당하는 사람
                    .reporterId(reporter.getUserId()) //신고자
                    .content(req.getContent())
                    .build();
            userReportRepository.save(userReport);

            if(userReportRepository.findByReporterUserId(req.getReportUserId()).size() >= 3) {
                // 신고 수 10 이상일 시 true 설정
                reportedUser.makeStatusReported();
            }

        } else {
            // 이미 신고를 했다면 리턴
            throw new AlreadyReport();
        }
    }

    @Override
    @Transactional
    public void reportBoard(PostReportRequest req) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User reporter = userRepository.findByName(authentication.getName()).orElseThrow(UserNotFound::new);
        Post reportedPost = postRepository.findById(req.getReportedPostId()).orElseThrow(PostNotFound::new);

        if(reporter.getUserId().equals(reportedPost.getUser().getUserId())) {
            throw new NotSelfReport();
        }

        if(postReportRepository.findByReportPostId(reportedPost.getPostId()) == null) {
            // 신고 한 적이 없다면, 테이블 생성 후 신고 처리
            PostReport postReport = PostReport.builder()
                    .reporterId(reporter.getUserId())
                    .reportPostId(reportedPost.getPostId())
                    .content(req.getContent())
                    .build();
            postReportRepository.save(postReport);

            if(postReportRepository.findByReportPostId(req.getReportedPostId()).size() >= 10) {
                // 신고 수 10 이상일 시 true 설정
                reportedPost.makeStatusReported();
            }

        } else {
            throw new AlreadyReport();
        }
    }

}
