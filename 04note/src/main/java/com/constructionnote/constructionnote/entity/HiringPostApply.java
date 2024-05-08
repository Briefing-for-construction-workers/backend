package com.constructionnote.constructionnote.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class HiringPostApply {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hiring_post_apply_id")
    private Long id;
    @Setter(AccessLevel.PROTECTED)
    private boolean isHired;
    private Timestamp createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hiring_post_id")
    private HiringPost hiringPost;

    @Builder
    public HiringPostApply(boolean isHired, Timestamp createdAt, User user, HiringPost hiringPost) {
        this.isHired = isHired;
        this.createdAt = createdAt;
        this.user = user;
        this.hiringPost = hiringPost;
    }

    public void pickApplicant() {
        this.isHired = true;
    }
}
