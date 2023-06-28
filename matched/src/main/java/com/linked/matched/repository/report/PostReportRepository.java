package com.linked.matched.repository.report;

import com.linked.matched.entity.Post;
import com.linked.matched.entity.PostReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface PostReportRepository extends JpaRepository<PostReport,Long> {

    List<PostReport> findByPost(Post post);


}
