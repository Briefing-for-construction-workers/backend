package com.constructionnote.constructionnote.api.response.user;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserProfileRes {
    private String nickname;
    private byte[] image;

    @Builder
    public UserProfileRes(String userId, String nickname, byte[] image) {
        this.nickname = nickname;
        this.image = image;
    }
}
