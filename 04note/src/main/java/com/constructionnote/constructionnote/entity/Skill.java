package com.constructionnote.constructionnote.entity;

import jakarta.persistence.*;

@Entity
public class Skill {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "skill_id")
    private Long id;
    private String pictureUrl;
    private String description;
}
