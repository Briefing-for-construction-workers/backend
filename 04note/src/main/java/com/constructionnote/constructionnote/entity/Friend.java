package com.constructionnote.constructionnote.entity;

import jakarta.persistence.*;

@Entity
public class Friend {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "friend_id")
    private Long id;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    private String stage;
}
