package com.linked.matched.service.match;

import com.linked.matched.entity.Applicant;
import com.linked.matched.entity.Post;
import com.linked.matched.entity.User;
import com.linked.matched.exception.AlreadyApplicant;
import com.linked.matched.exception.NotApplicant;
import com.linked.matched.exception.PostNotFound;
import com.linked.matched.exception.UserNotFound;
import com.linked.matched.repository.match.ApplicantRepository;
import com.linked.matched.repository.post.PostRepository;
import com.linked.matched.repository.user.UserRepository;
import com.linked.matched.response.post.PostResponse;
import com.linked.matched.response.user.SelectUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicantService {

    private final ApplicantRepository applicantRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Transactional
    public boolean applyPost(Long postId, Principal principal){
        User user = userRepository.findById(Long.valueOf(principal.getName())).orElseThrow(() -> new UserNotFound());

        Post post = postRepository.findById(postId).orElseThrow(() -> new PostNotFound());

        if(applicantRepository.findByUserAndPost(user,post).isPresent()){
            return false;
        }

        Applicant applicant = Applicant.builder()
                .user(user)
                .post(post)
                .build();
        applicantRepository.save(applicant);
        return true;
    }

    @Transactional
    public void cancelApply(Long postId, Principal principal){
        User user = userRepository.findById(Long.valueOf(principal.getName())).orElseThrow(() -> new UserNotFound());

        Post post = postRepository.findById(postId).orElseThrow(() -> new PostNotFound());

        Applicant applicant = applicantRepository.findByUserAndPost(user, post).orElseThrow(() -> new NotApplicant());

        applicantRepository.delete(applicant);

    }

    public List<SelectUser> findUserAndPost(Long postId) {

        Post post = postRepository.findById(postId).orElseThrow(() -> new PostNotFound());

        return applicantRepository.getUserList(post);

    }

    public boolean check(Long applicantId, Long postId) {
        User user = userRepository.findById(applicantId).orElseThrow(() -> new UserNotFound());

        Post post = postRepository.findById(postId).orElseThrow(() -> new PostNotFound());

        applicantRepository.findByUserAndPost(user, post).orElseThrow(() -> new NotApplicant());

        return true;
    }
    //1번 userid를 가지고 postlist를 가지고온다.
    //2번 postId를 선택하면 applicantuserlist를 준다.

    @Transactional
    public void cancelMatch(Long applicantId,Long postId){
        User user = userRepository.findById(applicantId).orElseThrow(() -> new UserNotFound());

        Post post = postRepository.findById(postId).orElseThrow(() -> new PostNotFound());

        Applicant applicant = applicantRepository.findByUserAndPost(user, post).orElseThrow(() -> new NotApplicant());

        applicantRepository.delete(applicant);

    }

    public List<PostResponse> applicantPosts(Principal principal) {
        User user = userRepository.findById(Long.valueOf(principal.getName())).orElseThrow(UserNotFound::new);

        return applicantRepository.getApplicantPosts(user);

    }
}
