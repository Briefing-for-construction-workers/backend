package com.constructionnote.constructionnote.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class User {
    @Id
    @Column(name = "user_id")
    private String id;
    private String address;
    private String level;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "profile_id")
    private Profile profile;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserSkill> userSkillList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Construction> constructionList = new ArrayList<>();

    @OneToMany
    @JoinColumn(name = "user_id")
    private List<Friend> friendList = new ArrayList<>();

    @OneToOne(mappedBy = "user")
    private SeekingPost seekingPost;

    @OneToMany
    @JoinColumn(name = "user_id")
    private List<Review> reviewList = new ArrayList<>();

    @Builder
    public User(String id, String address, String level) {
        this.id = id;
        this.address = address;
        this.level = level;
    }

    public void putProfile(Profile profile) {
        this.profile = profile;
    }

    public void addUserSkill(UserSkill userSkill) {
        this.userSkillList.add(userSkill);
    }
}
