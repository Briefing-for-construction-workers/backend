package com.constructionnote.constructionnote.controller.community;

import com.constructionnote.constructionnote.api.request.community.HiringReviewReq;
import com.constructionnote.constructionnote.api.response.community.HiringReviewRes;
import com.constructionnote.constructionnote.dto.community.PostDto;
import com.constructionnote.constructionnote.service.community.HiringReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PutMapping("/review/{reviewid}")
    public ResponseEntity<?> updateReview(@PathVariable("reviewid") Long reviewId, @RequestBody HiringReviewReq hiringReviewReq) {
        try {
            hiringReviewService.updateHiringReview(reviewId, hiringReviewReq);
            return new ResponseEntity<>("success", HttpStatus.CREATED);
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    @GetMapping("/review/{userid}")
    public ResponseEntity<?> list(@PathVariable("userid") String userId) {
        try {
            return new ResponseEntity<List<HiringReviewRes>>(hiringReviewService.viewReviewList(userId), HttpStatus.OK);
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    private ResponseEntity<?> exceptionHandling(Exception e) {
        e.printStackTrace();
        return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
