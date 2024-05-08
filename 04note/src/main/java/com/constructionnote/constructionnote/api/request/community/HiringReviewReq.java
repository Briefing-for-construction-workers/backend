package com.constructionnote.constructionnote.api.request.community;

import lombok.Getter;

@Getter
public class HiringReviewReq {
    private String reviewerId;
    private String revieweeId;
    private Long hiringPostId;
    private String content;
}
