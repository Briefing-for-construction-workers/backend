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
    private String level;
    private boolean state;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_code")
    private Address address;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "profile_id")
    private Profile profile;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserSkill> userSkillList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Construction> constructionList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Post> postList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<PostLike> postLikeList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<HiringPostApply> hiringPostApplyList = new ArrayList<>();

    @OneToMany
    @JoinColumn(name = "user_id")
    private List<Friend> friendList = new ArrayList<>();

    @OneToMany
    @JoinColumn(name = "reviewer_id")
    private List<Review> reviewerList = new ArrayList<>();

    @OneToMany
    @JoinColumn(name = "reviewee_id")
    private List<Review> revieweeList = new ArrayList<>();

    @Builder
    public User(String id, String level, boolean state) {
        this.id = id;
        this.level = level;
        this.state = state;
    }

    public void updateLevel(String level) {
        this.level = level;
    }

    public void updateState() {
        state = !this.state;
    }

    public void putAddress(Address address) { this.address = address; }

    public void putProfile(Profile profile) {
        this.profile = profile;
    }

    public void addUserSkill(UserSkill userSkill) {
        this.userSkillList.add(userSkill);
    }
}
