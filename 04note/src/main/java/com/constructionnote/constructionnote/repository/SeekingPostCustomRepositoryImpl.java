package com.constructionnote.constructionnote.repository;

import com.constructionnote.constructionnote.dto.community.StateConstants;
import com.constructionnote.constructionnote.entity.QSeekingPost;
import com.constructionnote.constructionnote.entity.SeekingPost;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.constructionnote.constructionnote.entity.QAddress.address;
import static com.constructionnote.constructionnote.entity.QSeekingPost.seekingPost;
import static com.constructionnote.constructionnote.entity.QUser.user;

@Repository
public class SeekingPostCustomRepositoryImpl implements SeekingPostCustomRepository {
    private final JPAQueryFactory jpaQueryFactory;

    public SeekingPostCustomRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public List<SeekingPost> findByAddressCodeAndKeyword(Pageable pageable, List<String> addressCodes, String keyword, String state) {
        return jpaQueryFactory.selectFrom(seekingPost)
                .leftJoin(seekingPost.user, user)
                .leftJoin(user.address, address)
                .where(address.addressCode.in(addressCodes)
                        .and(containsKeyword(keyword))
                        .and(stateCondition(state))
                        .and(seekingPost.deleted.isFalse()))
                .orderBy(seekingPost.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    private BooleanExpression containsKeyword(String keyword) {
        if (keyword == null || keyword.isEmpty()) {
            return null;
        }
        return QSeekingPost.seekingPost.title.containsIgnoreCase(keyword)
                .or(QSeekingPost.seekingPost.content.containsIgnoreCase(keyword));
    }

    private BooleanExpression stateCondition(String state) {
        if (StateConstants.ALL.equals(state)) {
            return null;
        } else if (StateConstants.TRUE.equals(state)) {
            return seekingPost.activated.isTrue();
        } else if (StateConstants.FALSE.equals(state)) {
            return seekingPost.activated.isFalse();
        } else {
            return null;
        }
    }
}
