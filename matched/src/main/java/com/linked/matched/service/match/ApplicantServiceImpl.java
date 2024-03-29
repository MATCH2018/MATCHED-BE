package com.linked.matched.service.match;

import com.linked.matched.entity.Applicant;
import com.linked.matched.entity.Post;
import com.linked.matched.entity.User;
import com.linked.matched.exception.post.NotApplicant;
import com.linked.matched.exception.post.PostNotFound;
import com.linked.matched.exception.post.SelfApplicant;
import com.linked.matched.exception.user.UserNotFound;
import com.linked.matched.repository.match.ApplicantRepository;
import com.linked.matched.repository.post.PostRepository;
import com.linked.matched.repository.user.UserRepository;
import com.linked.matched.response.post.PostResponse;
import com.linked.matched.response.user.SelectUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ApplicantServiceImpl implements ApplicantService{

    private final ApplicantRepository applicantRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Override
    @Transactional
    public boolean applyPost(Long postId, Long id){
        User user = userRepository.findById(Long.valueOf(id)).orElseThrow(() -> new UserNotFound());

        Post post = postRepository.findById(postId).orElseThrow(() -> new PostNotFound());

        if(applicantRepository.findByUserAndPost(user,post).isPresent()) {
            return false;
        }else if(post.getUser().equals(user)){
            throw new SelfApplicant();
        }

        Applicant applicant = Applicant.builder()
                .user(user)
                .post(post)
                .build();
        applicantRepository.save(applicant);
        return true;
    }

    @Override
    @Transactional
    public void cancelApply(Long postId,Long id){
        User user = userRepository.findById(Long.valueOf(id)).orElseThrow(() -> new UserNotFound());

        Post post = postRepository.findById(postId).orElseThrow(() -> new PostNotFound());

        Applicant applicant = applicantRepository.findByUserAndPost(user, post).orElseThrow(() -> new NotApplicant());

        applicantRepository.delete(applicant);

    }

    @Override
    public List<SelectUser> findUserAndPost(Long postId) {

        Post post = postRepository.findById(postId).orElseThrow(() -> new PostNotFound());

        return userRepository.getUserList(post).stream()
                .map(SelectUser::new).collect(Collectors.toList());

    }

    @Override
    public boolean check(Long applicantId, Long postId) {
        User user = userRepository.findById(applicantId).orElseThrow(() -> new UserNotFound());

        Post post = postRepository.findById(postId).orElseThrow(() -> new PostNotFound());

        applicantRepository.findByUserAndPost(user, post).orElseThrow(() -> new NotApplicant());

        return true;
    }

    @Override
    @Transactional
    public void cancelMatch(Long applicantId,Long postId){
        User user = userRepository.findById(applicantId).orElseThrow(() -> new UserNotFound());

        Post post = postRepository.findById(postId).orElseThrow(() -> new PostNotFound());

        Applicant applicant = applicantRepository.findByUserAndPost(user, post).orElseThrow(() -> new NotApplicant());

        applicantRepository.delete(applicant);

    }

    @Override
    public List<PostResponse> applicantPosts(Long id) {
        User user = userRepository.findById(Long.valueOf(id)).orElseThrow(UserNotFound::new);

        return postRepository.getApplicantPosts(user).stream()
                .map(PostResponse::new)
                .collect(Collectors.toList());
    }
}
