package com.linked.matched.controller.match;

import com.linked.matched.config.jwt.UserPrincipal;
import com.linked.matched.response.ResponseDto;
import com.linked.matched.response.post.PostResponse;
import com.linked.matched.service.match.ApplicantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ApplicantController {

    private final ApplicantService applicantService;

    @PostMapping("/match/{postId}") //매칭 지원
    public ResponseEntity<Object> apply(@PathVariable Long postId, @AuthenticationPrincipal UserPrincipal userPrincipal){
        if(applicantService.applyPost(postId,userPrincipal.getUserId())) {
            return new ResponseEntity<>(new ResponseDto("매칭 지원이 되었습니다."), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ResponseDto("이미 지원이 되었습니다."), HttpStatus.CONFLICT);
    }

    @DeleteMapping("/match/{postId}") //매칭 지원 취소
    public ResponseEntity<Object> cancelApply(@PathVariable Long postId, @AuthenticationPrincipal UserPrincipal userPrincipal){
        applicantService.cancelApply(postId,userPrincipal.getUserId());
        return new ResponseEntity<>(new ResponseDto("매칭 지원을 취소 했습니다."), HttpStatus.OK);

    }
    
    @GetMapping("/match/applicant")//지원자가 매칭 지원한 게시글 목록 조회
    public List<PostResponse> getListApplicant(@AuthenticationPrincipal UserPrincipal userPrincipal){
        return applicantService.applicantPosts(userPrincipal.getUserId());
    }

}
