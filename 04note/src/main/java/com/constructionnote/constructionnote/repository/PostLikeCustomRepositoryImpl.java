package com.constructionnote.constructionnote.repository;

import com.constructionnote.constructionnote.entity.HiringPost;
import com.constructionnote.constructionnote.entity.SeekingPost;
import com.querydsl.jpa.impl.JPAQueryFactory;

import java.util.List;

import static com.constructionnote.constructionnote.entity.QHiringPost.hiringPost;
import static com.constructionnote.constructionnote.entity.QPostLike.postLike;
import static com.constructionnote.constructionnote.entity.QSeekingPost.seekingPost;
import static com.constructionnote.constructionnote.entity.QUser.user;

public class PostLikeCustomRepositoryImpl implements PostLikeCustomRepository {
    private final JPAQueryFactory jpaQueryFactory;

    public PostLikeCustomRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public List<HiringPost> findLikedHiringPostsByUserId(String userId) {
        return jpaQueryFactory
                .select(hiringPost)
                .from(postLike)
                .join(postLike.post, hiringPost._super)
                .join(postLike.user, user)
                .where(user.id.eq(userId)
                        .and(postLike.deleted.isFalse()))
                .orderBy(postLike.createdAt.desc())
                .fetch();
    }

    @Override
    public List<SeekingPost> findLikedSeekingPostsByUserId(String userId) {
        return jpaQueryFactory
                .select(seekingPost)
                .from(postLike)
                .join(postLike.post, seekingPost._super)
                .join(postLike.user, user)
                .where(user.id.eq(userId)
                        .and(postLike.deleted.isFalse()))
                .orderBy(postLike.createdAt.desc())
                .fetch();
    }
}
