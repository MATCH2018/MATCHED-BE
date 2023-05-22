package com.linked.matched.controller.match;

import com.linked.matched.response.ResponseDto;
import com.linked.matched.response.post.PostResponse;
import com.linked.matched.response.user.SelectUser;
import com.linked.matched.response.user.UserMail;
import com.linked.matched.service.match.ApplicantService;
import com.linked.matched.service.post.PostService;
import com.linked.matched.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MatchController {

    private final UserService userService;
    private final PostService postService;
    private final ApplicantService applicantService;

    @GetMapping("/{userId}/match")
    public List<PostResponse> postList(@PathVariable Long userId){//list로 받는데 querydsl을 사용해야한다.
       return postService.findPostUser(userId);
    }

    @GetMapping("/{userId}/match/{postId}")
    public List<SelectUser> postUser(@PathVariable Long userId, @PathVariable Long postId){
        return applicantService.findUserAndPost(postId);
    }

    //내 게시글 매칭 현황
    @PostMapping("/{postId}/accept/{applicantId}") //이 userId는 상대 userId이다.
    public UserMail acceptUser(@PathVariable Long applicantId,@PathVariable Long postId){//학교 이메일을 준다.
        if(applicantService.check(applicantId,postId)){
            UserMail userEmail = userService.findUserEmail(applicantId);

            applicantService.cancelApply(applicantId, postId);
            return userEmail;
        }
        return null;
    }
    
    //매칭 취소 시키기
    @PostMapping("/{postId}/refuse/{applicantId}")//매칭을 거부햇다는 메시지를 남겨야한다.
    public ResponseEntity<Object> refuseUser(@PathVariable Long postId, @PathVariable Long applicantId){
        if(applicantService.check(applicantId,postId)){
            applicantService.cancelApply(applicantId, postId);
            return new ResponseEntity<>(new ResponseDto("매칭이 거부되었습니다."), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ResponseDto("지원한 매칭이 없습니다."), HttpStatus.OK);

    }
}
