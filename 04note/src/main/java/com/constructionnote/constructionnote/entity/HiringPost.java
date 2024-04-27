package com.constructionnote.constructionnote.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Timestamp;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class HiringPost {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hiring_post_id")
    private Long id;
    private String title;
    private Date date;
    private String location;
    private String level;
    private String skill;
    private Integer pay;
    private String content;
    private boolean state;
    private Timestamp createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User employer;

    @Builder
    public HiringPost(String title, Date date, String location, String level, String skill, Integer pay, String content, Timestamp createdAt, boolean state, User employer) {
        this.title = title;
        this.date = date;
        this.location = location;
        this.level = level;
        this.skill = skill;
        this.pay = pay;
        this.content = content;
        this.createdAt = createdAt;
        this.state = state;
        this.employer = employer;
    }
}
