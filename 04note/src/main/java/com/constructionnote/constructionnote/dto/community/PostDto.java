package com.constructionnote.constructionnote.dto.community;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Schema(title = "구인구직 게시글 미리보기 응답 DTO")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostDto {
    @Schema(description = "게시글id", example = "1")
    private Long postId;
    @Schema(description = "게시글 종류", example = "구인")
    private String postType;
    @Schema(description = "게시글 상태", example = "구인중")
    private String state;
    @Schema(description = "제목", example = "잘 하시는 분 1명 구합니다.")
    private String title;
    @Schema(description = "보유 기술", example = "[\"장판\"]")
    private List<String> skills  = new ArrayList<>();
    @Schema(description = "숙련도", example = "기공")
    private String level;
    @Schema(description = "일정", example = "2024-05-03")
    private Date date;
    @Schema(description = "게시글 올리고 지난 시간", example = "5분전")
    private String relativeTime;

    @Builder
    public PostDto(Long postId, String postType, String state, String title, List<String> skills, String level, Date date, String relativeTime) {
        this.postId = postId;
        this.postType = postType;
        this.state = state;
        this.title = title;
        this.skills = skills;
        this.level = level;
        this.date = date;
        this.relativeTime = relativeTime;
    }
}
