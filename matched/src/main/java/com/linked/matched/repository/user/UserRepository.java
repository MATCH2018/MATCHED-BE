package com.linked.matched.repository.user;

import com.linked.matched.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByLoginId(String loginId);

    Optional<User> findByName(String name);
}
