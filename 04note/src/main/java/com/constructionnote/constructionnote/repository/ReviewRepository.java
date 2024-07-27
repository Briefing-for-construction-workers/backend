package com.constructionnote.constructionnote.repository;

import com.constructionnote.constructionnote.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long>, ReviewCustomRepository {
}
