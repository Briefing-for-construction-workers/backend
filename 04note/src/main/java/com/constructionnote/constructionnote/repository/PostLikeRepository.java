package com.constructionnote.constructionnote.repository;

import com.constructionnote.constructionnote.entity.Post;
import com.constructionnote.constructionnote.entity.PostLike;
import com.constructionnote.constructionnote.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    Optional<PostLike> findByUserAndPost(User user, Post post);

    @Modifying
    @Query("UPDATE PostLike pl SET pl.deleted = false WHERE pl.id= :postLikeId")
    void reSave(@Param("postLikeId") Long postLikeId);
}
