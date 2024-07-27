package com.constructionnote.constructionnote.repository;

import com.constructionnote.constructionnote.entity.Post;
import com.constructionnote.constructionnote.entity.QAddress;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.constructionnote.constructionnote.entity.QHiringPost.hiringPost;
import static com.constructionnote.constructionnote.entity.QPost.post;
import static com.constructionnote.constructionnote.entity.QSeekingPost.seekingPost;
import static com.constructionnote.constructionnote.entity.QUser.user;

@Repository
public class PostCustomRepositoryImpl implements PostCustomRepository {
    private final JPAQueryFactory jpaQueryFactory;

    public PostCustomRepositoryImpl(JPAQueryFactory jpaQueryFactory){
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public List<Post> findByAddressCodeAndKeyword(Pageable pageable, List<String> addressCodes, String keyword) {
        QAddress hiringAddress = new QAddress("hiringAddress");
        QAddress seekingAddress = new QAddress("seekingAddress");

        return jpaQueryFactory.selectFrom(post)
                .leftJoin(hiringPost).on(post.eq(hiringPost._super))
                .leftJoin(seekingPost).on(post.eq(seekingPost._super))
                .leftJoin(hiringPost.address, hiringAddress)
                .leftJoin(seekingPost.user, user)
                .leftJoin(user.address, seekingAddress)
                .where((hiringAddress.addressCode.in(addressCodes)
                                .or(seekingAddress.addressCode.in(addressCodes)))
                        .and(post.deleted.eq(false))
                        .and(post.title.containsIgnoreCase(keyword)
                                .or(post.content.containsIgnoreCase(keyword))))
                .orderBy(post.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }
}
