package com.constructionnote.constructionnote.service;

import com.constructionnote.constructionnote.api.request.ConstructionReq;
import com.constructionnote.constructionnote.api.response.ConstructionRes;

public interface ConstructionService {
    void registerConstruction(ConstructionReq constructionReq);
    ConstructionRes viewConstruction(Long constructionId);
}
