package com.constructionnote.constructionnote.entity;

import jakarta.persistence.*;

@Entity
public class SeekingPost {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seeking_post_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
