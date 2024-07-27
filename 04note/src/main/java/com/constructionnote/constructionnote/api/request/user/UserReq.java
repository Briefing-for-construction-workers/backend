package com.constructionnote.constructionnote.api.request.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Schema(title = "회원가입 요청 DTO")
@Getter
public class UserReq {
    @Schema(description = "유저id(토큰명)", example = "1")
    private String userId;
    @Schema(description = "닉네임", example = "김이름")
    private String nickname;
    @Schema(description = "거점 지역의 법정동 코드", example = "1111012600")
    private String fullCode;
    @Schema(description = "숙련도", example = "기공")
    private String level;
    @Schema(description = "보유 기술", example = "[\"철거\", \"장판\", \"건물\"]")
    private List<String> skills  = new ArrayList<>();
}
