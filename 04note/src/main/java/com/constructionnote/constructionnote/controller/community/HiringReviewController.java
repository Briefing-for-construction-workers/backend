package com.constructionnote.constructionnote.controller.community;

import com.constructionnote.constructionnote.api.request.community.ReviewReq;
import com.constructionnote.constructionnote.service.community.HiringReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "구인 후기 컨트롤러", description = "구인 후기 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/hiring")
public class HiringReviewController {
    private final HiringReviewService hiringReviewService;

    @Operation(summary = "구인 후기 등록", description = "구인 게시글에 후기를 등록")
    @PostMapping("/review")
    public ResponseEntity<?> writeReview(@RequestBody ReviewReq hiringReviewReq) {
        try {
            Long hiringReviewId = hiringReviewService.createHiringReview(hiringReviewReq);
            return new ResponseEntity<>(hiringReviewId, HttpStatus.CREATED);
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    @Operation(summary = "구인 후기 수정", description = "해당 후기를 수정")
    @PutMapping("/review/{reviewid}")
    public ResponseEntity<?> updateReview(@PathVariable("reviewid")
                                              @Schema(description = "리뷰id", example = "1")
                                              Long reviewId,
                                          @RequestBody ReviewReq hiringReviewReq) {
        try {
            hiringReviewService.updateHiringReview(reviewId, hiringReviewReq);
            return new ResponseEntity<>("success", HttpStatus.CREATED);
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    @Operation(summary = "구인 후기 삭제", description = "해당 후기를 삭제")
    @DeleteMapping("/review/{reviewid}")
    public ResponseEntity<?> deleteReview(@PathVariable("reviewid")
                                              @Schema(description = "리뷰id", example = "1")
                                              Long reviewId) {
        try {
            hiringReviewService.deleteHiringReview(reviewId);
            return new ResponseEntity<>("success", HttpStatus.OK);
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    private ResponseEntity<?> exceptionHandling(Exception e) {
        e.printStackTrace();
        return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
