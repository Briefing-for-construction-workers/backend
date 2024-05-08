package com.constructionnote.constructionnote.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.sql.Timestamp;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@SQLDelete(sql = "UPDATE hiring_review SET deleted = true WHERE hiring_review_id = ?")
@SQLRestriction("deleted = false")
public class HiringReview {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hiring_review_id")
    private Long id;
    private String content;
    private Timestamp createdAt;
    private boolean deleted = Boolean.FALSE;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewer_id")
    private User reviewer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewee_id")
    private User reviewee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hiring_post_id")
    private HiringPost hiringPost;

    @Builder
    public HiringReview(String content, Timestamp createdAt, User reviewer, User reviewee, HiringPost hiringPost) {
        this.content = content;
        this.createdAt = createdAt;
        this.reviewer = reviewer;
        this.reviewee = reviewee;
        this.hiringPost = hiringPost;
    }

    public void updateHiringReview(String content) {
        this.content = content;
    }
}
