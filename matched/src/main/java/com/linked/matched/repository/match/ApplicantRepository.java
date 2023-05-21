package com.linked.matched.repository.match;

import com.linked.matched.entity.Applicant;
import com.linked.matched.entity.Post;
import com.linked.matched.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface ApplicantRepository extends JpaRepository<Applicant,Long>,ApplicantRepositoryCustom {
    Optional<Applicant> findByUserAndPost(User user, Post post);
}
