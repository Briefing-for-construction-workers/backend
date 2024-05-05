package com.constructionnote.constructionnote.service.community;

import com.constructionnote.constructionnote.api.request.community.HiringReviewReq;
import com.constructionnote.constructionnote.api.response.community.HiringReviewRes;

import java.util.List;

public interface HiringReviewService {
    Long createHiringReview(HiringReviewReq hiringReviewReq);
    void updateHiringReview(Long reviewId, HiringReviewReq hiringReviewReq);
    List<HiringReviewRes> viewReviewList(String userId) throws Exception;
    List<HiringReviewRes> viewLimitedReviewList(String userId) throws Exception;
}
