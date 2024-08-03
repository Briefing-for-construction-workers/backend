package com.constructionnote.constructionnote.repository;

import com.constructionnote.constructionnote.entity.HiringPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HiringPostRepository extends JpaRepository<HiringPost, Long>, HiringPostCustomRepository {
    @Query("SELECT hp FROM HiringPost hp WHERE hp.user.id = :userId ORDER BY hp.createdAt DESC")
    List<HiringPost> findByUserIdOrderByCreatedAtDesc(@Param("userId") String userId);
}
