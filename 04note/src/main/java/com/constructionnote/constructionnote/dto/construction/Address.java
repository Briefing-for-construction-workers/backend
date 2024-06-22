package com.constructionnote.constructionnote.dto.construction;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(title = "공사 주소 DTO")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Address {
    @Schema(description = "시도", example = "서울특별시")
    private String city;
    @Schema(description = "시군구", example = "강남구")
    private String district;
    @Schema(description = "읍면동", example = "개포2동")
    private String dong;

    @Builder
    public Address(String city, String district, String dong) {
        this.city = city;
        this.district = district;
        this.dong = dong;
    }
}
