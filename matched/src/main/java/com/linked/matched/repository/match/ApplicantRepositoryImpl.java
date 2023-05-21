package com.linked.matched.repository.match;

import com.linked.matched.entity.Post;
import com.linked.matched.entity.QApplicant;
import com.linked.matched.entity.QUser;
import com.linked.matched.response.user.SelectUser;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.linked.matched.entity.QApplicant.*;
import static com.linked.matched.entity.QUser.user;

@RequiredArgsConstructor
public class ApplicantRepositoryImpl implements ApplicantRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public List<SelectUser> getUserList(Post post) {

        return jpaQueryFactory.select(Projections.constructor(SelectUser.class,user.userId,user.name))
                .from(applicant)
                .join(applicant.user ,user).fetchJoin()
                .where(applicant.post.eq(post))
                .fetch();
    }
}
