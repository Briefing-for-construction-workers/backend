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
    private String nickname;
    private byte[] image;
    private List<String> skills  = new ArrayList<>();
    private String content;
    private String relativeTime;

    @Builder
    public HiringReviewRes(Long reviewId, String nickname, byte[] image, List<String> skills, String content, String relativeTime) {
        this.reviewId = reviewId;
        this.nickname = nickname;
        this.image = image;
        this.skills = skills;
        this.content = content;
        this.relativeTime = relativeTime;
    }
}
