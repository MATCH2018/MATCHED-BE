package com.linked.matched.repository.post;


import com.linked.matched.entity.Post;
import com.linked.matched.entity.User;
import com.linked.matched.entity.status.BoardStatus;
import com.linked.matched.request.post.PostSearch;
import com.linked.matched.response.post.PostOneResponse;

import java.util.List;

public interface PostRepositoryCustom {

    List<Post> getList(BoardStatus boardStatus, PostSearch postSearch);

    List<Post> findAllKeywordOrderByCreatedAt(BoardStatus boardStatus,String keyword, PostSearch postSearch);

    List<Post> findAllCategory();

    PostOneResponse getPostAndUser(Long postId);

    List<Post> getApplicantPosts(User user);
}
