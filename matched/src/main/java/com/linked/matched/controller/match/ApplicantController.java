package com.linked.matched.controller.match;

import com.linked.matched.service.match.ApplicantService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ApplicantController {

    private final ApplicantService applicantService;

    @PostMapping("/match/{postId}/{userId}")
    public void apply(@PathVariable Long userId,@PathVariable Long postId){
        applicantService.applyPost(userId,postId);
    }

    @DeleteMapping("/match/{postId}/{userId}")
    public void cancelApply(@PathVariable Long userId,@PathVariable Long postId){
        applicantService.cancelApply(userId, postId);
    }
    //매칭현황을 넣을껀지


}
