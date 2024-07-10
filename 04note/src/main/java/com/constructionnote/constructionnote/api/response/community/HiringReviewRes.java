package com.constructionnote.constructionnote.api.response.community;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HiringReviewRes {
    private Long reviewId;
    private List<String> skills  = new ArrayList<>();
    private Integer rate;
    private String content;
    private String relativeTime;

    @Builder
    public HiringReviewRes(Long reviewId, List<String> skills, Integer rate, String content, String relativeTime) {
        this.reviewId = reviewId;
        this.skills = skills;
        this.rate = rate;
        this.content = content;
        this.relativeTime = relativeTime;
    }
}
