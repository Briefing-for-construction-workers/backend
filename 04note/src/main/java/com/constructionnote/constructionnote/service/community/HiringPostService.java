package com.constructionnote.constructionnote.service.community;

import com.constructionnote.constructionnote.api.request.community.HiringPostApplyReq;
import com.constructionnote.constructionnote.api.request.community.HiringPostLikeReq;
import com.constructionnote.constructionnote.api.request.community.HiringPostReq;
import com.constructionnote.constructionnote.api.response.community.HiringPostDetailRes;

public interface HiringPostService {
    Long registerHiringPost(HiringPostReq hiringPostReq);
    HiringPostDetailRes viewHiringPostById(Long hiringPostId) throws Exception;
    void updateHiringPost(Long hiringPostId, HiringPostReq hiringPostReq);

    Long likeHiringPost(HiringPostLikeReq hiringPostLikeReq);

    Long applyHiringPost(HiringPostApplyReq hiringPostApplyReq);
    void pickApplicant(HiringPostApplyReq hiringPostApplyReq);
}
