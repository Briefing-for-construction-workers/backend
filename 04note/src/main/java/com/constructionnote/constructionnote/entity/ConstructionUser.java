package com.constructionnote.constructionnote.entity;

import jakarta.persistence.*;

@Entity
public class ConstructionUser {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "construction_user_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "construction_id")
    private Construction construction;
}
