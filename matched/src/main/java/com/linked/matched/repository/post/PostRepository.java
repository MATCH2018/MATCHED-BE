package com.linked.matched.repository.post;

import com.linked.matched.entity.Post;
import com.linked.matched.entity.status.BoardStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface PostRepository extends JpaRepository<Post,Long>,PostRepositoryCustom {

}
