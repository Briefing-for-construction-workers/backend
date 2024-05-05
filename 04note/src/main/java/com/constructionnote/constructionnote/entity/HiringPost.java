package com.constructionnote.constructionnote.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@DiscriminatorValue("hiring")
public class HiringPost extends Post {
    private Date date;
    private String location;
    private String level;
    private Integer pay;
    private boolean state;

    @OneToMany(mappedBy = "hiringPost", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostSkill> postSkillList = new ArrayList<>();

    @OneToMany(mappedBy = "hiringPost")
    private List<HiringPostApply> hiringPostApplyList = new ArrayList<>();

    @Builder(builderMethodName = "builder1", buildMethodName = "build1")
    public HiringPost(String title, Date date, String location, String level, Integer pay, String content, Timestamp createdAt, boolean state, User user) {
        setTitle(title);
        this.date = date;
        this.location = location;
        this.level = level;
        this.pay = pay;
        setContent(content);
        setCreatedAt(createdAt);
        this.state = state;
        setUser(user);
    }

    @Builder(builderMethodName = "builder2", buildMethodName = "build2")
    public HiringPost(Long postId, String title, Date date, String location, String level, Integer pay, String content, Timestamp createdAt, boolean state, User user) {
        setId(postId);
        setTitle(title);
        this.date = date;
        this.location = location;
        this.level = level;
        this.pay = pay;
        setContent(content);
        setCreatedAt(createdAt);
        this.state = state;
        setUser(user);
    }

    public void addPostSkill(PostSkill postSkill) {
        this.postSkillList.add(postSkill);
    }
}
