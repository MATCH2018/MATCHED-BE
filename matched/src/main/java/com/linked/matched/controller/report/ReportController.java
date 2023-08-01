package com.linked.matched.controller.report;

import com.linked.matched.request.report.PostReportRequest;
import com.linked.matched.response.ResponseDto;
import com.linked.matched.service.repository.ReportService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/reports/boards") // 게시글 신고
    public ResponseEntity<Object> reportBoard(@RequestBody PostReportRequest boardReportRequest){
        reportService.reportBoard(boardReportRequest);
        return new ResponseEntity<>(new ResponseDto("신고가 완료 되었습니다."), HttpStatus.OK);

    }
}
