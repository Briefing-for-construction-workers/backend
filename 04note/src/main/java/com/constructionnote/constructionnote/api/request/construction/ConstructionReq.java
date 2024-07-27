package com.constructionnote.constructionnote.api.request.construction;

import com.constructionnote.constructionnote.dto.construction.ConstructionSite;
import com.constructionnote.constructionnote.dto.construction.Schedule;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Schema(title = "공사 기록 요청 DTO")
@Getter
public class ConstructionReq {
    @Schema(description = "유저id(토큰명)", example = "1")
    private String userId;
    @Schema(description = "공사 종류", example = "바닥 공사")
    private String kind;
    private Schedule schedule;
    private ConstructionSite constructionSite;
    @Schema(description = "기타 정보", example = "대금은 현금으로 17일에 받을 예정")
    private String memo;
}
