package com.linked.matched.service.match;

import com.linked.matched.response.post.PostResponse;
import com.linked.matched.response.user.SelectUser;

import java.util.List;

public interface ApplicantService {

    boolean applyPost(Long postId, Long id);

    void cancelApply(Long postId,Long id);

    List<SelectUser> findUserAndPost(Long postId);

    boolean check(Long applicantId, Long postId);

    void cancelMatch(Long applicantId,Long postId);

    List<PostResponse> applicantPosts(Long id);
}
