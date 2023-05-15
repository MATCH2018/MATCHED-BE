package com.linked.matched.repository.report;

import com.linked.matched.entity.UserReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface UserReportRepository extends JpaRepository<UserReport,Long> {

    List<UserReport> findByReporterUserId(Long reportedId);

}
