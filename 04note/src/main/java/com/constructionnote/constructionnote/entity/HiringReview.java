package com.constructionnote.constructionnote.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class HiringReview {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hiring_review_id")
    private Long id;
    private String content;
    private Timestamp createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewer_id")
    private User reviewer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewee_id")
    private User reviewee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hiring_post_id")
    private HiringPost hiringPost;

    @Builder(builderMethodName = "builder1", buildMethodName = "build1")
    public HiringReview(String content, Timestamp createdAt, User reviewer, User reviewee, HiringPost hiringPost) {
        this.content = content;
        this.createdAt = createdAt;
        this.reviewer = reviewer;
        this.reviewee = reviewee;
        this.hiringPost = hiringPost;
    }

    @Builder(builderMethodName = "builder2", buildMethodName = "build2")
    public HiringReview(Long id, String content, Timestamp createdAt, User reviewer, User reviewee, HiringPost hiringPost) {
        this.id = id;
        this.content = content;
        this.createdAt = createdAt;
        this.reviewer = reviewer;
        this.reviewee = reviewee;
        this.hiringPost = hiringPost;
    }
}
