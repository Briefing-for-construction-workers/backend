package com.constructionnote.constructionnote.api.request.community;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Schema(title = "구인 게시글 요청 DTO")
@Getter
public class HiringPostReq {
    @Schema(description = "유저id(토큰명)", example = "1")
    private String userId;
    @Schema(description = "제목", example = "잘 하시는 분 1명 구합니다.")
    private String title;
    @Schema(description = "일정", example = "2024-05-03")
    private Date date;
    @Schema(description = "공사 장소(법정동 코드)", example = "1168010300")
    private String fullCode;
    @Schema(description = "숙련도", example = "기공")
    private String level;
    @Schema(description = "보유 기술", example = "[\"장판\"]")
    private List<String> skills  = new ArrayList<>();
    @Schema(description = "일급", example = "2000000")
    private Integer pay;
    @Schema(description = "설명", example = "같이 열심히 하실 분 연락주세요!")
    private String content;
}
