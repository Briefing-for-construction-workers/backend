package com.constructionnote.constructionnote.service.community;

import com.constructionnote.constructionnote.api.request.community.SeekingPostReq;

public interface SeekingPostService {
    Long registerSeekingPost(SeekingPostReq seekingPostReq);
    void updateSeekingPost(Long postId, SeekingPostReq seekingPostReq);
    void deleteSeekingPost(Long postId);
}
