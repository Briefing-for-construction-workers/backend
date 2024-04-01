package com.constructionnote.constructionnote.dto.construction;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Address {
    private String city;
    private String district;
    private String dong;

    @Builder
    public Address(String city, String district, String dong) {
        this.city = city;
        this.district = district;
        this.dong = dong;
    }
}
