package com.constructionnote.constructionnote.api.request;

import lombok.Getter;

@Getter
public class HiringPostLikeReq {
    private String userId;
    private Long hiringPostId;
}
