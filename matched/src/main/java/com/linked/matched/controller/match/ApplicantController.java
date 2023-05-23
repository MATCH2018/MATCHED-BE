package com.linked.matched.controller.match;

import com.linked.matched.response.ResponseDto;
import com.linked.matched.service.match.ApplicantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ApplicantController {

    private final ApplicantService applicantService;

    @PostMapping("/match/{postId}/{userId}") // 매칭 지원이 되었습니다.
    public ResponseEntity<Object> apply(@PathVariable Long userId, @PathVariable Long postId){
        applicantService.applyPost(userId,postId);
        return new ResponseEntity<>(new ResponseDto("매칭 지원이 되었습니다."), HttpStatus.OK);

    }

    @DeleteMapping("/match/{postId}/{userId}") // 매칭 지원을 취소 했습니다.
    public ResponseEntity<Object> cancelApply(@PathVariable Long userId,@PathVariable Long postId){
        applicantService.cancelApply(userId, postId);
        return new ResponseEntity<>(new ResponseDto("매칭 지원을 취소 했습니다."), HttpStatus.OK);

    }
    //내가 매칭 지원한 게시글


}
