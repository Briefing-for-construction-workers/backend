package com.constructionnote.constructionnote.service.construction;

import com.constructionnote.constructionnote.api.request.construction.ConstructionReq;
import com.constructionnote.constructionnote.api.response.construction.ConstructionRes;

public interface ConstructionService {
    Long registerConstruction(ConstructionReq constructionReq);
    ConstructionRes viewConstruction(Long constructionId);
    Long updateConstruction(Long constructionId, ConstructionReq constructionReq);
}
