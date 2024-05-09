package com.constructionnote.constructionnote.api.request.community;

import lombok.Getter;

@Getter
public class HiringPostLikeReq {
    private String userId;
    private Long postId;
}
