package com.linked.matched.controller.match;

import com.linked.matched.response.ResponseDto;
import com.linked.matched.service.match.ApplicantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
public class ApplicantController {

    private final ApplicantService applicantService;

    @PostMapping("/match/{postId}") // 매칭 지원이 되었습니다.
    public ResponseEntity<Object> apply(@PathVariable Long postId, Principal principal){
        applicantService.applyPost(postId,principal);
        return new ResponseEntity<>(new ResponseDto("매칭 지원이 되었습니다."), HttpStatus.OK);

    }

    @DeleteMapping("/match/{postId}") // 매칭 지원을 취소 했습니다.
    public ResponseEntity<Object> cancelApply(@PathVariable Long postId, Principal principal){
        applicantService.cancelApply(postId,principal);
        return new ResponseEntity<>(new ResponseDto("매칭 지원을 취소 했습니다."), HttpStatus.OK);

    }
    //내가 매칭 지원한 게시글


}
