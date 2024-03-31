package com.constructionnote.constructionnote.api.request;

import com.constructionnote.constructionnote.dto.construction.ConstructionSite;
import com.constructionnote.constructionnote.dto.construction.Schedule;
import lombok.Getter;

@Getter
public class ConstructionReq {
    private String userId;
    private String kind;
    private Schedule schedule;
    private ConstructionSite constructionSite;
    private String memo;
}
