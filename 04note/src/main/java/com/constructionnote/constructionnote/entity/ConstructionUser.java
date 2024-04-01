package com.constructionnote.constructionnote.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class ConstructionUser {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "construction_user_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "construction_id")
    private Construction construction;

    @Builder
    public ConstructionUser(User user, Construction construction) {
        this.user = user;
        this.construction = construction;
    }

}
