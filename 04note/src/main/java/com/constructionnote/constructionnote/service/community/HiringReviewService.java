package com.constructionnote.constructionnote.service.community;

import com.constructionnote.constructionnote.api.request.community.ReviewReq;
import com.constructionnote.constructionnote.api.response.community.ReviewRes;

import java.util.List;

public interface HiringReviewService {
    Long createHiringReview(ReviewReq hiringReviewReq);
    void updateHiringReview(Long reviewId, ReviewReq hiringReviewReq);
    List<ReviewRes> viewReviewList(String userId);
    void deleteHiringReview(Long reviewId);
}
