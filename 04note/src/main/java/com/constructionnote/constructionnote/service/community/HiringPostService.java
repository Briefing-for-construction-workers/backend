package com.constructionnote.constructionnote.service.community;

import com.constructionnote.constructionnote.api.request.community.HiringPostApplyReq;
import com.constructionnote.constructionnote.api.request.community.HiringPostReq;
import com.constructionnote.constructionnote.api.response.community.HiringPostDetailRes;
import com.constructionnote.constructionnote.dto.community.PostDto;

import java.util.List;

public interface HiringPostService {
    Long registerHiringPost(HiringPostReq hiringPostReq);
    HiringPostDetailRes viewHiringPostById(Long postId) throws Exception;
    void updateHiringPost(Long postId, HiringPostReq hiringPostReq);
    void deleteHiringPost(Long postId);
    List<PostDto> viewHiringPostByUserId(String userId);

    Long applyHiringPost(HiringPostApplyReq hiringPostApplyReq);
    void pickApplicant(HiringPostApplyReq hiringPostApplyReq);

    void updateHiringState(Long postId);
}
