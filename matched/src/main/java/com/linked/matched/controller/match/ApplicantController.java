package com.linked.matched.controller.match;

import com.linked.matched.response.ResponseDto;
import com.linked.matched.response.post.PostResponse;
import com.linked.matched.service.match.ApplicantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ApplicantController {

    private final ApplicantService applicantService;

    @PostMapping("/match/{postId}") //매칭 지원
    public ResponseEntity<Object> apply(@PathVariable Long postId, Principal principal){
        if(applicantService.applyPost(postId,principal)) {
            return new ResponseEntity<>(new ResponseDto("매칭 지원이 되었습니다."), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ResponseDto("이미 지원이 되었습니다."), HttpStatus.CONFLICT);
    }

    @DeleteMapping("/match/{postId}") //매칭 지원 취소
    public ResponseEntity<Object> cancelApply(@PathVariable Long postId, Principal principal){
        applicantService.cancelApply(postId,principal);
        return new ResponseEntity<>(new ResponseDto("매칭 지원을 취소 했습니다."), HttpStatus.OK);

    }
    
    @GetMapping("/match/applicant")//지원자가 매칭 지원한 게시글 목록 조회
    public List<PostResponse> getListApplicant(Principal principal){
        return applicantService.applicantPosts(principal);
    }

}
