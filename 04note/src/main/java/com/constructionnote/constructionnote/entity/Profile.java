package com.constructionnote.constructionnote.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "profile_id")
    private Long id;
    private String nickname;
    private String imageUrl;
    private String fileName;

    @Builder
    public Profile(String nickname, String imageUrl, String fileName) {
        this.nickname = nickname;
        this.imageUrl = imageUrl;
        this.fileName = fileName;
    }

    public void updateProfile(String nickname, String imageUrl, String fileName) {
        this.nickname = nickname;
        this.imageUrl = imageUrl;
        this.fileName = fileName;
    }
}
