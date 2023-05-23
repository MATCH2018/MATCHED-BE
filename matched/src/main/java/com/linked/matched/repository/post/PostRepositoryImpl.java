package com.linked.matched.repository.post;


import com.linked.matched.entity.Post;
import com.linked.matched.entity.QPost;
import com.linked.matched.entity.QUser;
import com.linked.matched.entity.status.BoardStatus;
import com.linked.matched.request.post.PostSearch;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.linked.matched.entity.QPost.post;
import static com.linked.matched.entity.QUser.*;

import java.util.ArrayList;
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

    @Override
    public List<Post> findAllKeywordOrderByCreatedAt(BoardStatus boardStatus,String keyword, PostSearch postSearch) {
        return jpaQueryFactory.selectFrom(post)
                .where(post.title.contains(keyword).or(post.content.contains(keyword)))
                .where(post.boardName.eq(boardStatus))
                .leftJoin(post.user, user).fetchJoin()
                .limit(postSearch.getSize())
                .offset(postSearch.getOffset())
                .orderBy(post.createdAt.desc())
                .distinct()
                .fetch();
    }

    @Override
    public List<Post> findAllCategory() {
        List<Post> result = new ArrayList<>();
        for(BoardStatus boardStatus:BoardStatus.values()) {
            List<Post> post = jpaQueryFactory.selectFrom(QPost.post)
                    .where(QPost.post.boardName.eq(boardStatus))
                    .orderBy(QPost.post.createdAt.desc())
                    .limit(4)
                    .fetch();
            result.addAll(post);
        }

        return result;
    }
}
