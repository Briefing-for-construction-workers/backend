package com.constructionnote.constructionnote.dto.construction;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(title = "공사 장소 DTO")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ConstructionSite {
    private Address address;
    @Schema(description = "현장명", example = "홍문빌라 101동 1002호")
    private String workSiteDescription;

    @Builder
    public ConstructionSite(Address address, String workSiteDescription) {
        this.address = address;
        this.workSiteDescription = workSiteDescription;
    }

    public static ConstructionSite createConstructionSite(String city, String district, String dong, String workSiteDescription) {
        return ConstructionSite.builder()
                .address(Address.builder()
                        .city(city)
                        .district(district)
                        .dong(dong)
                        .build())
                .workSiteDescription(workSiteDescription)
                .build();
    }
}
