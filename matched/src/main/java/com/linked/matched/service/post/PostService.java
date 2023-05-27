package com.linked.matched.service.post;

import com.linked.matched.request.post.PostCreate;
import com.linked.matched.request.post.PostEdit;
import com.linked.matched.request.post.PostSearch;
import com.linked.matched.response.post.PostOneResponse;
import com.linked.matched.response.post.PostResponse;

import java.security.Principal;
import java.util.List;

public interface PostService {

    List<PostResponse> getList(String boardName, PostSearch postSearch);

    PostOneResponse findPost(Long postId);

    void write(PostCreate postCreate,Principal principal);

    Boolean edit(Long postId, PostEdit postEdit, Principal principal);

    Boolean delete(Long postId, Principal principal);

    List<PostResponse> findPostUser(Principal principal);

    List<PostResponse> searchPost(String boardName,String keyword, PostSearch postSearch);

    List<PostResponse> homeList();
}
