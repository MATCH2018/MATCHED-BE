package com.linked.matched.service.post;

import com.linked.matched.entity.Post;
import com.linked.matched.entity.status.BoardStatus;
import com.linked.matched.repository.post.PostRepository;
import com.linked.matched.request.post.PostCreate;
import com.linked.matched.request.post.PostEdit;
import com.linked.matched.response.post.PostResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public List<PostResponse> getList(String boardName) {
        //일단 넣어주야하는 값들이 이름을 넣어주면 그 이름에 대한 list값을 준다.
        //return 값 줘야한다.-querydsl 사용해야한다.??? 그냥 값을 넣어주고 뽑는다?

        //dto로 변경시켜서 사용하기?
        return postRepository.findByBoardName(BoardStatus.valueOf(boardName)).stream()
                .map(PostResponse::new)
                .collect(Collectors.toList());
    }

    public PostResponse findPost(Long postId) {
        //목록중 내가 원하는 정보찾기
        //return 값 줘야한다. - querydsl 사용해야한다. 바뀔수 있다. 분류를 먼저하고 다시 찾을 수 있다.
        return postRepository.findById(postId).map(PostResponse::new).orElseThrow(IllegalArgumentException::new);
    }

    public void write(PostCreate postCreate) {
        Post post = Post.builder()
                .title(postCreate.getTitle())
                .content(postCreate.getContent())
                .limitPeople(postCreate.getLimitPeople())
                .boardName(postCreate.getBoardName())
                .build();

        postRepository.save(post);
    }

    @Transactional
    public void edit(Long postId, PostEdit postEdit) {
        //게시글 정정
        //리턴 값 필요 없음?-그냥 기본으로 할까? 흠.....
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException());

        post.edit(postEdit);
    }

    public void delete(Long postId) {
        //삭제
        //리턴 값 필요 없음
        //예외처리 해줘야한다.
        Post post = null;
        try {
            post = postRepository.findById(postId).orElseThrow(() -> new IllegalAccessException());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        postRepository.delete(post);
    }
}
