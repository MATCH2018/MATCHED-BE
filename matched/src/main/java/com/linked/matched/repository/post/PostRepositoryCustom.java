package com.linked.matched.repository.post;


import com.linked.matched.entity.Post;
import com.linked.matched.entity.status.BoardStatus;
import com.linked.matched.request.post.PostSearch;

import java.util.List;

public interface PostRepositoryCustom {

    //getList해서 페이징 처리를 해서 받는 값을 처리해야한다.
    List<Post> getList(BoardStatus boardStatus, PostSearch postSearch);

    List<Post> findAllKeywordOrderByCreatedAt(BoardStatus boardStatus,String keyword, PostSearch postSearch);

    List<Post> findAllCategory();
}
