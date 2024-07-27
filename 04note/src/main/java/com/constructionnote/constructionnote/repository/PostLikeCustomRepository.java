package com.constructionnote.constructionnote.repository;

import com.constructionnote.constructionnote.entity.HiringPost;

import java.util.List;

public interface PostLikeCustomRepository {
    List<HiringPost> findLikedHiringPostsByUserId(String userId);
}
