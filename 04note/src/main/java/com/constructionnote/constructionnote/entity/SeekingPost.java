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
public class SeekingPost {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seeking_post_id")
    private Long id;
    private String title;
    private String content;
    private Timestamp createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne
    @JoinColumn(name = "construction_id")
    private Construction construction;

    @Builder
    public SeekingPost(String title, String content, Timestamp createdAt, User user, Construction construction) {
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.user = user;
        this.construction = construction;
    }
}
