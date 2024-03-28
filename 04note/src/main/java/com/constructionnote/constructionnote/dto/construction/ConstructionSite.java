package com.constructionnote.constructionnote.dto.construction;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ConstructionSite {
    private Address address;
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
