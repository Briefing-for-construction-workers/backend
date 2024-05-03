package com.constructionnote.constructionnote.controller.community;

import com.constructionnote.constructionnote.api.request.community.HiringReviewReq;
import com.constructionnote.constructionnote.service.community.HiringReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/hiring")
public class HiringReviewController {
    private final HiringReviewService hiringReviewService;

    @PostMapping("/review")
    public ResponseEntity<?> writeReview(@RequestBody HiringReviewReq hiringReviewReq) {
        try {
            Long hiringReviewId = hiringReviewService.createHiringReview(hiringReviewReq);
            return new ResponseEntity<>(hiringReviewId, HttpStatus.CREATED);
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    private ResponseEntity<?> exceptionHandling(Exception e) {
        e.printStackTrace();
        return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
