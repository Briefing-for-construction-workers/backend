package com.constructionnote.constructionnote.service.community;

import com.constructionnote.constructionnote.api.request.community.ReviewReq;
import com.constructionnote.constructionnote.api.response.community.ReviewRes;

import java.util.List;

public interface SeekingReviewService {
    Long createSeekingReview(ReviewReq seekingReviewReq);
    void updateSeekingReview(Long reviewId, ReviewReq seekingReviewReq);
    void deleteSeekingReview(Long reviewId);
    List<ReviewRes> viewSeekingReviewList(String userId);
}
