package com.constructionnote.constructionnote.controller.community;

import com.constructionnote.constructionnote.api.request.community.ReviewReq;
import com.constructionnote.constructionnote.api.response.community.ReviewRes;
import com.constructionnote.constructionnote.service.community.SeekingReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "구직 후기 컨트롤러", description = "구직 후기 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/seeking")
public class SeekingReviewController {
    private final SeekingReviewService seekingReviewService;

    @Operation(summary = "구직 후기 등록", description = "구직 게시글에 후기를 등록")
    @PostMapping("/review")
    public ResponseEntity<?> writeReview(@RequestBody ReviewReq seekingReviewReq) {
        try {
            Long seekingReviewId = seekingReviewService.createSeekingReview(seekingReviewReq);
            return new ResponseEntity<>(seekingReviewId, HttpStatus.CREATED);
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    @Operation(summary = "구직 후기 수정", description = "해당 구직 후기를 수정")
    @PutMapping("/review/{reviewid}")
    public ResponseEntity<?> updateReview(@PathVariable("reviewid")
                                              @Schema(description = "리뷰id", example = "1")
                                              Long reviewId,
                                          @RequestBody ReviewReq seekingReviewReq) {
        try {
            seekingReviewService.updateSeekingReview(reviewId, seekingReviewReq);
            return new ResponseEntity<>("success", HttpStatus.CREATED);
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    @Operation(summary = "구직 후기 삭제", description = "해당 후기를 삭제")
    @DeleteMapping("/review/{reviewid}")
    public ResponseEntity<?> deleteReview(@PathVariable("reviewid")
                                          @Schema(description = "리뷰id", example = "1")
                                          Long reviewId) {
        try {
            seekingReviewService.deleteSeekingReview(reviewId);
            return new ResponseEntity<>("success", HttpStatus.OK);
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    @Operation(summary = "내 구직 후기 조회", description = "나에게 작성된 구직 후기 조회")
    @GetMapping("/review/{userid}")
    public ResponseEntity<?> list(@PathVariable("userid")
                                  @Schema(description = "유저id(토큰명)", example = "1")
                                  String userId) {
        try {
            return new ResponseEntity<List<ReviewRes>>(seekingReviewService.viewSeekingReviewList(userId), HttpStatus.OK);
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    private ResponseEntity<?> exceptionHandling(Exception e) {
        e.printStackTrace();
        return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
