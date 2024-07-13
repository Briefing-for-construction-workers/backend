package com.constructionnote.constructionnote.repository;

import com.constructionnote.constructionnote.entity.Review;

import java.util.List;

public interface ReviewCustomRepository {
    List<Review> findReviewsByRevieweeAndPostTypeHiring(String revieweeId);
}
