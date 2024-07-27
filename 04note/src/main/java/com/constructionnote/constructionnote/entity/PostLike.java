package com.constructionnote.constructionnote.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;

import java.sql.Timestamp;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@SQLDelete(sql = "UPDATE post_like SET deleted = true WHERE post_like_id = ?")
public class PostLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_like_id")
    private Long id;
    private Timestamp createdAt;
    private boolean deleted = Boolean.FALSE;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @Builder
    public PostLike(Timestamp createdAt, User user, Post post) {
        this.createdAt = createdAt;
        this.user = user;
        this.post = post;
    }
}
