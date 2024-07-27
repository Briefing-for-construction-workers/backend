package com.constructionnote.constructionnote.dto.user;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProfileDto {
    private String nickname;
    private String imageUrl;
    private List<String> skills  = new ArrayList<>();

    @Builder
    public ProfileDto(String nickname, String imageUrl, List<String> skills) {
        this.nickname = nickname;
        this.imageUrl = imageUrl;
        this.skills = skills;
    }
}
