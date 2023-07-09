package com.linked.matched.repository.user;

import com.linked.matched.entity.Post;
import com.linked.matched.entity.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.linked.matched.entity.QApplicant.applicant;
import static com.linked.matched.entity.QUser.user;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<User> getUserList(Post post) {
        return jpaQueryFactory.selectFrom(user)
                .join(user.applicant ,applicant)
                .where(applicant.post.eq(post))
                .distinct()
                .fetch();
    }
}
