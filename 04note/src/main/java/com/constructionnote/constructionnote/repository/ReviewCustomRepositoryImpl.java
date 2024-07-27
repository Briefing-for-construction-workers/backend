package com.constructionnote.constructionnote.repository;

import com.constructionnote.constructionnote.entity.HiringPost;
import com.constructionnote.constructionnote.entity.PostType;
import com.constructionnote.constructionnote.entity.Review;
import com.constructionnote.constructionnote.entity.SeekingPost;
import com.querydsl.jpa.impl.JPAQueryFactory;

import java.util.List;

import static com.constructionnote.constructionnote.entity.QPost.post;
import static com.constructionnote.constructionnote.entity.QReview.review;

public class ReviewCustomRepositoryImpl implements ReviewCustomRepository {
    private final JPAQueryFactory jpaQueryFactory;

    public ReviewCustomRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public List<Review> findReviewsByRevieweeAndPostTypeHiring(String revieweeId) {
        return jpaQueryFactory.selectFrom(review)
                .join(review.post, post).fetchJoin()
                .where(review.reviewee.id.eq(revieweeId)
                        .and(review.type.eq(PostType.HIRING))
                        .and(review.post.instanceOf(HiringPost.class))
                        .and(review.deleted.isFalse()))
                .orderBy(review.createdAt.desc())
                .fetch();
    }

    @Override
    public List<Review> findReviewsByRevieweeAndPostTypeSeeking(String revieweeId) {
        return jpaQueryFactory.selectFrom(review)
                .join(review.post, post).fetchJoin()
                .where(review.reviewee.id.eq(revieweeId)
                        .and(review.type.eq(PostType.SEEKING))
                        .and(review.post.instanceOf(SeekingPost.class))
                        .and(review.deleted.isFalse()))
                .orderBy(review.createdAt.desc())
                .fetch();
    }
}
