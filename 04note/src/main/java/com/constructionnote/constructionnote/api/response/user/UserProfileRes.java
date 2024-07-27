package com.constructionnote.constructionnote.api.response.user;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserProfileRes {
    private String nickname;
    private String imageUrl;

    @Builder
    public UserProfileRes(String nickname, String imageUrl) {
        this.nickname = nickname;
        this.imageUrl = imageUrl;
    }
}
