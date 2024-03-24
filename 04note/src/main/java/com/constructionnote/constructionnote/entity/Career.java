package com.constructionnote.constructionnote.entity;

import jakarta.persistence.*;

@Entity
public class Career {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "career_id")
    private Long id;
}
