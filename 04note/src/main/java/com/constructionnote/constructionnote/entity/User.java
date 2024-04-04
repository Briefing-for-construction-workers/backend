package com.constructionnote.constructionnote.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class User {
    @Id
    @Column(name = "user_id")
    private String id;
    private String nickname;
    private String profileUrl;

    @OneToMany(mappedBy = "user")
    private List<Construction> constructionList = new ArrayList<>();

    @OneToMany
    @JoinColumn(name = "user_id")
    private List<Friend> friendList = new ArrayList<>();

    @OneToOne(mappedBy = "user")
    private SeekingPost seekingPost;

    @OneToMany
    @JoinColumn(name = "user_id")
    private List<Career> careerList = new ArrayList<>();

    @OneToMany
    @JoinColumn(name = "user_id")
    private List<Skill> skillList = new ArrayList<>();

    @OneToMany
    @JoinColumn(name = "user_id")
    private List<Review> reviewList = new ArrayList<>();

    @Builder
    public User(String id) {
        this.id = id;
    }
}
