package com.linked.matched.service.post;

import com.linked.matched.config.jwt.UserPrincipal;
import com.linked.matched.request.post.PostCreate;
import com.linked.matched.request.post.PostEditor;
import com.linked.matched.request.post.PostSearch;
import com.linked.matched.response.post.PostOneResponse;
import com.linked.matched.response.post.PostResponse;

import java.util.List;

public interface PostService {

    List<PostResponse> getList(String boardName, Integer page);

    PostOneResponse findPost(Long postId);

    void write(PostCreate postCreate, Long id );

    Boolean edit(Long postId, PostEditor postEdit, Long id);

    Boolean delete(Long postId, String loginId);

    List<PostResponse> findPostUser(Long id);

    List<PostResponse> searchPost(String boardName,String keyword, PostSearch postSearch);

    List<PostResponse> homeList();
}
