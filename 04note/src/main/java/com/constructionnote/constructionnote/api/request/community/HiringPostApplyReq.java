package com.constructionnote.constructionnote.api.request.community;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Schema(title = "구인 게시글 지원 요청 DTO")
@Getter
public class HiringPostApplyReq {
    @Schema(description = "유저id(토큰명)", example = "1")
    private String userId;
    @Schema(description = "게시글id", example = "1")
    private Long postId;
}
