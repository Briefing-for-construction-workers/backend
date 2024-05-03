package com.constructionnote.constructionnote.api.request.community;

import lombok.Getter;

@Getter
public class SeekingPostReq {
    private String userId;
    private String title;
    private String content;
    private Long constructionId;
}
