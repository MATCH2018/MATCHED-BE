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
import com.linked.matched.response.user.SelectUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicantService {

    private final ApplicantRepository applicantRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Transactional
    public void applyPost(Long userId,Long postId){
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFound());

        Post post = postRepository.findById(postId).orElseThrow(() -> new PostNotFound());

        if(applicantRepository.findByUserAndPost(user,post).isPresent()){
            throw new AlreadyApplicant();
        }

        Applicant.builder()
                .user(user)
                .post(post)
                .build();
    }

    @Transactional
    public void cancelApply(Long userId,Long postId){
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFound());

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
}
