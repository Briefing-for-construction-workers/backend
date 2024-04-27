package com.constructionnote.constructionnote.service;

import com.constructionnote.constructionnote.api.request.HiringPostApplyReq;
import com.constructionnote.constructionnote.api.request.HiringPostLikeReq;
import com.constructionnote.constructionnote.api.request.HiringPostReq;
import com.constructionnote.constructionnote.api.response.HiringPostDetailRes;

public interface HiringPostService {
    Long registerHiringPost(HiringPostReq hiringPostReq);
    HiringPostDetailRes viewHiringPostById(Long hiringPostId) throws Exception;

    Long likeHiringPost(HiringPostLikeReq hiringPostLikeReq);

    Long applyHiringPost(HiringPostApplyReq hiringPostApplyReq);
}
