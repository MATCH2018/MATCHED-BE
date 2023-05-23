package com.linked.matched.service.post;

import com.linked.matched.entity.Post;
import com.linked.matched.entity.User;
import com.linked.matched.entity.status.BoardStatus;
import com.linked.matched.exception.PostNotFound;
import com.linked.matched.exception.UserNotFound;
import com.linked.matched.repository.post.PostRepository;
import com.linked.matched.repository.user.UserRepository;
import com.linked.matched.request.post.PostCreate;
import com.linked.matched.request.post.PostEdit;
import com.linked.matched.request.post.PostSearch;
import com.linked.matched.response.post.PostResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService{

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public List<PostResponse> getList(String boardName, PostSearch postSearch) {
        //일단 넣어주야하는 값들이 이름을 넣어주면 그 이름에 대한 list값을 준다.
        //return 값 줘야한다.-querydsl 사용해야한다.??? 그냥 값을 넣어주고 뽑는다?

        //dto로 변경시켜서 사용하기?
        return postRepository.getList(BoardStatus.valueOf(boardName),postSearch).stream()
                .map(PostResponse::new)
                .collect(Collectors.toList());
    }

    public PostResponse findPost(Long postId) {
        //목록중 내가 원하는 정보찾기
        //return 값 줘야한다. - querydsl 사용해야한다. 바뀔수 있다. 분류를 먼저하고 다시 찾을 수 있다.
        return postRepository.findById(postId).map(PostResponse::new).orElseThrow(PostNotFound::new);
    }

    public void write(PostCreate postCreate) {

        User user = userRepository.findById(postCreate.getUserId()).orElseThrow(() -> new UserNotFound());

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
    public void edit(Long postId, PostEdit postEdit) {
        //게시글 정정
        //리턴 값 필요 없음?-그냥 기본으로 할까? 흠.....
        Post post = postRepository.findById(postId).orElseThrow(PostNotFound::new);

        post.edit(postEdit);
    }

    public void delete(Long postId) {
        //삭제
        //리턴 값 필요 없음
        //예외처리 해줘야한다.
        Post post = postRepository.findById(postId).orElseThrow(PostNotFound::new);

        postRepository.delete(post);
    }

    @Override
    public List<PostResponse> findPostUser(Long userId) {

        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFound());

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
