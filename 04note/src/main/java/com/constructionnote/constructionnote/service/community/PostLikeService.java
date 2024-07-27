package com.constructionnote.constructionnote.service.community;

import com.constructionnote.constructionnote.api.request.community.PostLikeReq;
import com.constructionnote.constructionnote.dto.community.PostDto;

import java.util.List;

public interface PostLikeService {
    String likePost(PostLikeReq postLikeReq);

    List<PostDto> viewLikedHiringPostsByUserId(String userId);
}
