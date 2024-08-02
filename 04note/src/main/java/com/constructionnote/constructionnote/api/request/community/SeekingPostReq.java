package com.constructionnote.constructionnote.api.request.community;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Schema(title = "구직 게시글 요청 DTO")
@Getter
public class SeekingPostReq {
    @Schema(description = "유저id(토큰명)", example = "1")
    private String userId;
    @Schema(description = "제목", example = "철거 14년 경력자 구직합니다.")
    private String title;
    @Schema(description = "내용", example = "안녕하세요.")
    private String content;
    @Schema(description = "공개할 과거 공사 기록 id", example = "1")
    private Long constructionId;
}
