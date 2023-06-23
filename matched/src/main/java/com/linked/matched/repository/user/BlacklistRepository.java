package com.linked.matched.repository.user;

import com.linked.matched.entity.Blacklist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlacklistRepository extends JpaRepository<Blacklist, Long> {
    Blacklist findByEmail(String email);
}