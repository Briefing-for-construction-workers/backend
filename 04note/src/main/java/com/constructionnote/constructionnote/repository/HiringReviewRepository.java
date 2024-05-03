package com.constructionnote.constructionnote.repository;

import com.constructionnote.constructionnote.entity.HiringReview;
import com.constructionnote.constructionnote.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HiringReviewRepository extends JpaRepository<HiringReview, Long> {
    List<HiringReview> findByReviewee(User reviewee);
    List<HiringReview> findTop3ByRevieweeOrderByCreatedAtDesc(User reviewee);
}
