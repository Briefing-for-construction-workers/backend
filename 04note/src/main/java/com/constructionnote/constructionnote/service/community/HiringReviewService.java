package com.constructionnote.constructionnote.service.community;

import com.constructionnote.constructionnote.api.request.community.HiringReviewReq;
import com.constructionnote.constructionnote.api.response.community.HiringReviewRes;

import java.util.List;

public interface HiringReviewService {
    Long createHiringReview(HiringReviewReq hiringReviewReq);
    List<HiringReviewRes> viewReviewList(String userId) throws Exception;
}
