package com.constructionnote.constructionnote.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class User {
    @Id
    @Column(name = "user_id")
    private String id;
    private String nickname;
    private String profileUrl;

    @OneToMany(mappedBy = "user")
    private List<ConstructionUser> constructionUserList = new ArrayList<>();

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

}
