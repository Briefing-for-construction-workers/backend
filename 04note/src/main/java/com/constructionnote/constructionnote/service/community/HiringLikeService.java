package com.constructionnote.constructionnote.service.community;

import com.constructionnote.constructionnote.api.request.community.PostLikeReq;

public interface HiringLikeService {
    Long likeHiringPost(PostLikeReq hiringPostLikeReq);
}
