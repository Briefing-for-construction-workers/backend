package com.constructionnote.constructionnote.api.response.community;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Schema(title = "구인 게시글 응답 DTO")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HiringPostRes {
    @Schema(description = "제목", example = "잘 하시는 분 1명 구합니다.")
    private String title;
    @Schema(description = "보유 기술", example = "[\"장판\"]")
    private List<String> skills  = new ArrayList<>();
    //
    private String location;
    @Schema(description = "일정", example = "2024-05-03")
    private Date date;
    @Schema(description = "숙련도", example = "기공")
    private String level;
    @Schema(description = "일급", example = "2000000")
    private Integer pay;
    @Schema(description = "설명", example = "같이 열심히 하실 분 연락주세요!")
    private String content;
    @Schema(description = "게시글 상태", example = "구인중")
    private String state;

    @Builder
    public HiringPostRes(String title, List<String> skills, String location, Date date, String level, Integer pay, String content, String state) {
        this.title = title;
        this.skills = skills;
        this.location = location;
        this.date = date;
        this.level = level;
        this.pay = pay;
        this.content = content;
        this.state = state;
    }
}
