package com.constructionnote.constructionnote.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("hiring")
@OnDelete(action = OnDeleteAction.CASCADE)
@Entity
public class HiringPost extends Post {
    private Date date;
    private String level;
    private Integer pay;
    private boolean state;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_code")
    private Address address;

    @OneToMany(mappedBy = "hiringPost", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostSkill> postSkillList = new ArrayList<>();

    @OneToMany(mappedBy = "hiringPost")
    private List<HiringPostApply> hiringPostApplyList = new ArrayList<>();

    @Builder
    public HiringPost(String title, Date date, String level, Integer pay, String content, Timestamp createdAt, boolean state, User user) {
        setTitle(title);
        this.date = date;
        this.level = level;
        this.pay = pay;
        setContent(content);
        setCreatedAt(createdAt);
        this.state = state;
        setUser(user);
    }

    public void updateHiringPost(String title, Date date, String level, Integer pay, String content) {
        setTitle(title);
        this.date = date;
        this.level = level;
        this.pay = pay;
        setContent(content);
    }

    public void putAddress(Address address) { this.address = address; }

    public void addPostSkill(PostSkill postSkill) {
        this.postSkillList.add(postSkill);
    }

    public void updateState() { state = !this.state; }

    public String stateToVocab() {
        return this.state ? "구직 완료" : "구직중";
    }
}
