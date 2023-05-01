package com.linked.matched.repository.post;


import com.linked.matched.entity.Post;
import com.linked.matched.entity.status.BoardStatus;
import com.linked.matched.request.post.PostSearch;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.linked.matched.entity.QPost.post;

import java.util.List;

@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Post> getList(BoardStatus boardStatus, PostSearch postSearch) {
        return jpaQueryFactory.selectFrom(post)
                .where(post.boardName.eq(boardStatus))
                .limit(postSearch.getSize())
                .offset(postSearch.getOffset())
                .orderBy(post.postId.desc())
                .fetch();

    }
}
