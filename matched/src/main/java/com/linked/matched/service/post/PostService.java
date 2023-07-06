package com.linked.matched.service.post;

import com.linked.matched.config.jwt.UserPrincipal;
import com.linked.matched.request.post.PostCreate;
import com.linked.matched.request.post.PostEdit;
import com.linked.matched.request.post.PostSearch;
import com.linked.matched.response.post.PostOneResponse;
import com.linked.matched.response.post.PostResponse;

import java.util.List;

public interface PostService {

    List<PostResponse> getList(String boardName, Integer page);

    PostOneResponse findPost(Long postId);

    void write(PostCreate postCreate, UserPrincipal userPrincipal);

    Boolean edit(Long postId, PostEdit postEdit, UserPrincipal userPrincipal);

    Boolean delete(Long postId, UserPrincipal userPrincipal);

    List<PostResponse> findPostUser(UserPrincipal userPrincipal);

    List<PostResponse> searchPost(String boardName,String keyword, PostSearch postSearch);

    List<PostResponse> homeList();
}
