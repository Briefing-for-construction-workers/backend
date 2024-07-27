package com.constructionnote.constructionnote.service.community;

import com.constructionnote.constructionnote.api.request.community.SeekingPostReq;
import com.constructionnote.constructionnote.dto.community.PostDto;

import java.util.List;

public interface SeekingPostService {
    Long registerSeekingPost(SeekingPostReq seekingPostReq);
    void updateSeekingPost(Long postId, SeekingPostReq seekingPostReq);
    void deleteSeekingPost(Long postId);

    List<PostDto> viewSeekingPostByUserId(String userId);
}
