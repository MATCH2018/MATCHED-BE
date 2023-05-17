package com.linked.matched.controller.report;

import com.linked.matched.request.report.PostReportRequest;
import com.linked.matched.request.report.UserReportRequest;
import com.linked.matched.service.user.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/reports/users")
    public void reportUser(@RequestBody UserReportRequest userReportRequest) {
        reportService.reportUser(userReportRequest);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/reports/boards")
    public void reportBoard(@RequestBody PostReportRequest boardReportRequest){
        reportService.reportBoard(boardReportRequest);
    }
}
