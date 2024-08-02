package com.constructionnote.constructionnote.api.request.community;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Schema(title = "리뷰 요청 DTO")
@Getter
public class ReviewReq {
    @Schema(description = "리뷰 작성자 id(토큰명)", example = "1")
    private String reviewerId;
    @Schema(description = "리뷰 대상자 id(토큰명)", example = "2")
    private String revieweeId;
    @Schema(description = "게시글 id(토큰명)", example = "1")
    private Long postId;
    @Schema(description = "별점", example = "5")
    private Integer rate;
    @Schema(description = "내용", example = "열심히 하십니다.")
    private String content;
}
