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
@SQLDelete(sql = "UPDATE review SET deleted = true WHERE hiring_review_id = ?")
@SQLRestriction("deleted = false")
public class Review {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;
    private String content;
    private int rate;
    private Timestamp createdAt;
    private boolean deleted = Boolean.FALSE;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewer_id")
    private User reviewer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewee_id")
    private User reviewee;

    @Enumerated(EnumType.STRING)
    private PostType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @Builder
    public Review(String content, int rate, Timestamp createdAt, User reviewer, User reviewee, PostType type, Post post) {
        this.content = content;
        this.rate = rate;
        this.createdAt = createdAt;
        this.reviewer = reviewer;
        this.reviewee = reviewee;
        this.type = type;
        this.post = post;
    }

    public void updateReview(String content) {
        this.content = content;
    }
}
