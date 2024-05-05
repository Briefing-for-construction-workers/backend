package com.constructionnote.constructionnote.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn
public abstract class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    @Setter(AccessLevel.PROTECTED)
    private Long id;
    @Setter(AccessLevel.PROTECTED)
    private String title;
    @Setter(AccessLevel.PROTECTED)
    private String content;
    @Setter(AccessLevel.PROTECTED)
    private Timestamp createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @Setter(AccessLevel.PROTECTED)
    private User user;
}
