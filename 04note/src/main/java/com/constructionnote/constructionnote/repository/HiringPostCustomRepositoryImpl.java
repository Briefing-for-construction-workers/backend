package com.constructionnote.constructionnote.repository;

import com.constructionnote.constructionnote.dto.community.StateConstants;
import com.constructionnote.constructionnote.entity.HiringPost;
import com.constructionnote.constructionnote.entity.QHiringPost;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.constructionnote.constructionnote.entity.QAddress.address;
import static com.constructionnote.constructionnote.entity.QHiringPost.hiringPost;

@Repository
public class HiringPostCustomRepositoryImpl implements HiringPostCustomRepository {
    private final JPAQueryFactory jpaQueryFactory;

    public HiringPostCustomRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public List<HiringPost> findByAddressCodeAndKeyword(Pageable pageable, List<String> addressCodes, String keyword, String state) {
        return jpaQueryFactory.selectFrom(hiringPost)
                .join(hiringPost.address, address).fetchJoin()
                .where(address.addressCode.in(addressCodes)
                        .and(containsKeyword(keyword))
                        .and(stateCondition(state))
                        .and(hiringPost.deleted.isFalse()))
                .orderBy(hiringPost.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    private BooleanExpression containsKeyword(String keyword) {
        if (keyword == null || keyword.isEmpty()) {
            return null;
        }
        return QHiringPost.hiringPost.title.containsIgnoreCase(keyword)
                .or(QHiringPost.hiringPost.content.containsIgnoreCase(keyword));
    }

    private BooleanExpression stateCondition(String state) {
        if (StateConstants.ALL.equals(state)) {
            return null;
        } else if (StateConstants.TRUE.equals(state)) {
            return QHiringPost.hiringPost.state.isTrue();
        } else if (StateConstants.FALSE.equals(state)) {
            return QHiringPost.hiringPost.state.isFalse();
        } else {
            return null;
        }
    }
}
