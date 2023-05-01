package com.linked.matched.service.post;

import com.linked.matched.request.post.PostCreate;
import com.linked.matched.request.post.PostEdit;
import com.linked.matched.request.post.PostSearch;
import com.linked.matched.response.post.PostResponse;

import java.util.List;

public interface PostService {

    List<PostResponse> getList(String boardName, PostSearch postSearch);

    PostResponse findPost(Long postId);

    void write(PostCreate postCreate);

    void edit(Long postId, PostEdit postEdit);

    void delete(Long postId);
}
