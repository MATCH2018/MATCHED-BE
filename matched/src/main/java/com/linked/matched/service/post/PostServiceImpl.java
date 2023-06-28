package com.linked.matched.service.post;

import com.linked.matched.entity.Post;
import com.linked.matched.entity.User;
import com.linked.matched.entity.status.BoardStatus;
import com.linked.matched.exception.post.PostNotFound;
import com.linked.matched.exception.user.UserNotFound;
import com.linked.matched.repository.post.PostRepository;
import com.linked.matched.repository.user.UserRepository;
import com.linked.matched.request.post.PostCreate;
import com.linked.matched.request.post.PostEdit;
import com.linked.matched.request.post.PostSearch;
import com.linked.matched.response.post.PostOneResponse;
import com.linked.matched.response.post.PostResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService{

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public List<PostResponse> getList(String boardName, Integer page) {
        PostSearch postSearch=new PostSearch(page);

        return postRepository.getList(BoardStatus.valueOf(boardName),postSearch).stream()
                .map(PostResponse::new)
                .collect(Collectors.toList());
    }

    public PostOneResponse findPost(Long postId) {
        return postRepository.getPostAndUser(postId);
    }

    public void write(PostCreate postCreate,Principal principal) {

        User user = userRepository.findById(Long.valueOf(principal.getName())).orElseThrow(() -> new UserNotFound());

        Post post = Post.builder()
                .title(postCreate.getTitle())
                .content(postCreate.getContent())
                .limitPeople(postCreate.getLimitPeople())
                .boardName(postCreate.getBoardName())
                .user(user)
                .build();

        postRepository.save(post);
    }

    @Transactional
    public Boolean edit(Long postId, PostEdit postEdit, Principal principal) {
        //게시글 정정
        Post post = postRepository.findById(postId).orElseThrow(PostNotFound::new);

        User user = userRepository.findById(Long.valueOf(principal.getName())).orElseThrow(UserNotFound::new);

        if(post.getUser().equals(user)||user.getAuthorityName().equals("ROLE_ADMIN")) {
            post.edit(postEdit);
            return true;
        }
        return false;
    }

    public Boolean delete(Long postId, Principal principal) {
        //삭제
        //리턴 값 필요 없음
        //예외처리 해줘야한다.
        Post post = postRepository.findById(postId).orElseThrow(PostNotFound::new);

        User user = userRepository.findById(Long.valueOf(principal.getName())).orElseThrow(UserNotFound::new);
        if(post.getUser().equals(user)||user.getAuthorityName().equals("ROLE_ADMIN")) {
            postRepository.delete(post);
            return true;
        }
        return false;
    }

    @Override
    public List<PostResponse> findPostUser(Principal principal) {

        User user = userRepository.findById(Long.valueOf(principal.getName())).orElseThrow(() -> new UserNotFound());

        return postRepository.findByUser(user).stream()
                .map(PostResponse::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<PostResponse> searchPost(String boardName, String keyword, PostSearch postSearch) {
        return postRepository.findAllKeywordOrderByCreatedAt(BoardStatus.valueOf(boardName),keyword,postSearch).stream()
                .map(PostResponse::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<PostResponse> homeList() {
        return postRepository.findAllCategory().stream()
                .map(PostResponse::new)
                .collect(Collectors.toList());
    }

}
