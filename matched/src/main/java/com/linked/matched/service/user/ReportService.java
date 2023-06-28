package com.linked.matched.service.user;

import com.linked.matched.request.report.PostReportRequest;
import com.linked.matched.request.report.UserReportRequest;

public interface ReportService {
    void reportBoard(PostReportRequest req);
}

