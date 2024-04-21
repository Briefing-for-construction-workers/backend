package com.constructionnote.constructionnote.api.request;

import lombok.Getter;

@Getter
public class SeekingPostReq {
    private String userId;
    private String title;
    private String content;
    private Integer pay;
}
