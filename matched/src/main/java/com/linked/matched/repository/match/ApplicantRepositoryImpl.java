package com.linked.matched.repository.match;

import com.linked.matched.entity.Post;
import com.linked.matched.entity.User;
import com.linked.matched.response.user.SelectUser;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.linked.matched.entity.QApplicant.*;
import static com.linked.matched.entity.QUser.user;
import static com.linked.matched.entity.QPost.post;
@Repository
@RequiredArgsConstructor
public class ApplicantRepositoryImpl implements ApplicantRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public List<SelectUser> getUserList(Post post) {
        return jpaQueryFactory.select(Projections.constructor(SelectUser.class,user.userId,user.name))
                .from(applicant)
                .leftJoin(applicant.user ,user).fetchJoin()
                .where(applicant.post.eq(post))
                .fetch();
    }

    @Override
    public List<Tuple> getApplicantPosts(User user) {
        return jpaQueryFactory.select(post.postId,post.title, post.content,post.createdAt, post.limitPeople,post.boardName)
                .from(applicant)
                .leftJoin(applicant.post,post).fetchJoin()
                .where(applicant.user.eq(user))
                .fetch();
    }
}
