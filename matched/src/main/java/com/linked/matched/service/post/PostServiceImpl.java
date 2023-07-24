package com.linked.matched.service.post;

import com.linked.matched.entity.Post;
import com.linked.matched.entity.User;
import com.linked.matched.entity.status.BoardStatus;
import com.linked.matched.exception.post.PostNotFound;
import com.linked.matched.exception.user.UserNotFound;
import com.linked.matched.repository.post.PostRepository;
import com.linked.matched.repository.user.UserRepository;
import com.linked.matched.request.post.PostCreate;
import com.linked.matched.request.post.PostEditor;
import com.linked.matched.request.post.PostSearch;
import com.linked.matched.response.post.PostOneResponse;
import com.linked.matched.response.post.PostResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Override
    public PostOneResponse findPost(Long postId) {
        return postRepository.getPostAndUser(postId);
    }

    @Override
    public void write(PostCreate postCreate,Long id) {

        User user = userRepository.findById(Long.valueOf(id)).orElseThrow(() -> new UserNotFound());

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
    @Override
    public Boolean edit(Long postId, PostEditor postEdit, Long id) {
        Post post = postRepository.findById(postId).orElseThrow(PostNotFound::new);

        User user = userRepository.findById(id).orElseThrow(UserNotFound::new);

        if(post.getUser().equals(user)||user.getAuthorityName().equals("ROLE_ADMIN")) {

            PostEditor.PostEditorBuilder postEditorBuilder = post.toEditor();

            if(postEdit.getTitle()!=null){
                postEditorBuilder.title(postEdit.getTitle());
            }
            if (postEdit.getContent()!=null){
                postEditorBuilder.content(postEdit.getContent());
            }
            if (postEdit.getBoardName()!=null){
                postEditorBuilder.boardName(postEdit.getBoardName());
            }
            if(postEdit.getLimitPeople()!=null){
                postEditorBuilder.limitPeople(postEdit.getLimitPeople());
            }

            post.edit(postEditorBuilder.build());
            return true;
        }
        return false;
    }

    @Transactional
    @Override
    public Boolean delete(Long postId, String loginId) {
        Post post = postRepository.findById(postId).orElseThrow(PostNotFound::new);

        User user = userRepository.findByLoginId(loginId).orElseThrow(UserNotFound::new);

        if(post.getUser().equals(user)||user.getAuthorityName().equals("ROLE_ADMIN")) {
            postRepository.deleteById(post.getPostId());
            return true;
        }
        return false;
    }

    @Override
    public List<PostResponse> findPostUser(Long id) {

        User user = userRepository.findById(Long.valueOf(id)).orElseThrow(() -> new UserNotFound());

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
