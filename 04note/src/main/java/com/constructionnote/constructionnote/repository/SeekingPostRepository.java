package com.constructionnote.constructionnote.repository;

import com.constructionnote.constructionnote.entity.SeekingPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SeekingPostRepository extends JpaRepository<SeekingPost, Long> {
    @Query("SELECT sp FROM SeekingPost sp WHERE sp.user.id = :userId ORDER BY sp.createdAt DESC")
    List<SeekingPost> findByUserIdOrderByCreatedAtDesc(@Param("userId") String userId);
}
