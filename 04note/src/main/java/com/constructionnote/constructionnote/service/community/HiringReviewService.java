package com.constructionnote.constructionnote.service.community;

import com.constructionnote.constructionnote.api.request.community.HiringReviewPostReq;
import com.constructionnote.constructionnote.api.request.community.HiringReviewUpdateReq;
import com.constructionnote.constructionnote.api.response.community.HiringReviewRes;

import java.util.List;

public interface HiringReviewService {
    Long createHiringReview(HiringReviewPostReq hiringReviewReq);
    void updateHiringReview(Long reviewId, HiringReviewUpdateReq hiringReviewReq);
    List<HiringReviewRes> viewReviewList(String userId) throws Exception;
    List<HiringReviewRes> viewLimitedReviewList(String userId) throws Exception;
    void deleteHiringReview(Long reviewId);
}
