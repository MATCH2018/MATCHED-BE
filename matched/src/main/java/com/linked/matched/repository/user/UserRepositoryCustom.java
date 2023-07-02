package com.linked.matched.repository.user;

import com.linked.matched.entity.Post;
import com.linked.matched.entity.User;

import java.util.List;

public interface UserRepositoryCustom {
    List<User> getUserList(Post post);
}
