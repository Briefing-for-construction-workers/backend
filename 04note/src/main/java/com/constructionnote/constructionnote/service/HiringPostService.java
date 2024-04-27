package com.constructionnote.constructionnote.service;

import com.constructionnote.constructionnote.api.request.HiringPostReq;
import com.constructionnote.constructionnote.api.response.HiringPostDetailRes;

public interface HiringPostService {
    Long registerHiringPost(HiringPostReq hiringPostReq);
    HiringPostDetailRes viewHiringPostById(Long hiringPostId) throws Exception;
}
