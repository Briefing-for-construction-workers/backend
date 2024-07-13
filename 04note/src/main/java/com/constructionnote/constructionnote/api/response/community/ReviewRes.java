package com.constructionnote.constructionnote.api.response.community;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Schema(title = "후기 응답 DTO")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewRes {
    @Schema(description = "리뷰 id", example = "1")
    private Long reviewId;
    @Schema(description = "스킬 리스트", example = "[\"장판\"]")
    private List<String> skills  = new ArrayList<>();
    @Schema(description = "별점", example = "5")
    private Integer rate;
    @Schema(description = "내용", example = "열심히 하십니다.")
    private String content;
    @Schema(description = "얼마 전 작성되었는지(방금/분/시간/일)", example = "1시간 전")
    private String relativeTime;

    @Builder
    public ReviewRes(Long reviewId, List<String> skills, Integer rate, String content, String relativeTime) {
        this.reviewId = reviewId;
        this.skills = skills;
        this.rate = rate;
        this.content = content;
        this.relativeTime = relativeTime;
    }
}
