package com.constructionnote.constructionnote.api.response;

import com.constructionnote.constructionnote.dto.construction.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ConstructionRes {
    private StatusType statusType;
    private String kind;
    private Schedule schedule;
    private ConstructionSite constructionSite;
    private String memo;

    @Builder
    public ConstructionRes(StatusType statusType, String kind, Schedule schedule, ConstructionSite constructionSite, String memo) {
        this.statusType = statusType;
        this.kind = kind;
        this.schedule = schedule;
        this.constructionSite = constructionSite;
        this.memo = memo;
    }
}
