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
    private String skill;
    private Integer pay;
    private boolean state;

    @OneToMany(mappedBy = "hiringPost")
    private List<HiringPostApply> hiringPostApplyList = new ArrayList<>();

    @Builder
    public HiringPost(String title, Date date, String location, String level, String skill, Integer pay, String content, Timestamp createdAt, boolean state, User user) {
        setTitle(title);
        this.date = date;
        this.location = location;
        this.level = level;
        this.skill = skill;
        this.pay = pay;
        setContent(content);
        setCreatedAt(createdAt);
        this.state = state;
        setUser(user);
    }


}
