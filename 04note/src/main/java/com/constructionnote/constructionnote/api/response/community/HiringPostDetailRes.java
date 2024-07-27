package com.constructionnote.constructionnote.api.response.community;

import com.constructionnote.constructionnote.dto.community.HiringPostDto;
import com.constructionnote.constructionnote.dto.user.ProfileDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HiringPostDetailRes {
    private ProfileDto profileDto;
    private String kind;
    private HiringPostDto hiringPostDto;

    @Builder
    public HiringPostDetailRes(ProfileDto profileDto, String kind, HiringPostDto hiringPostDto) {
        this.profileDto = profileDto;
        this.kind = kind;
        this.hiringPostDto = hiringPostDto;
    }
}
