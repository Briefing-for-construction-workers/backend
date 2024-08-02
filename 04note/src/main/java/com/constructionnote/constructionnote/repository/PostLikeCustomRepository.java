package com.constructionnote.constructionnote.repository;

import com.constructionnote.constructionnote.entity.HiringPost;
import com.constructionnote.constructionnote.entity.SeekingPost;

import java.util.List;

public interface PostLikeCustomRepository {
    List<HiringPost> findLikedHiringPostsByUserId(String userId);
    List<SeekingPost> findLikedSeekingPostsByUserId(String userId);
}
