package com.constructionnote.constructionnote.api.request.community;

import lombok.Getter;

@Getter
public class HiringReviewReq {
    private String reviewerId;
    private String revieweeId;
    private Long postId;
    private Integer rate;
    private String content;
}
