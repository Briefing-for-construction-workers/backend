package com.constructionnote.constructionnote.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class SeekingPost {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seeking_post_id")
    private Long id;
    private String title;
    private String content;
    private Integer pay;
    private Timestamp regDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public SeekingPost(String title, String content, Integer pay, Timestamp regDate, User user) {
        this.title = title;
        this.content = content;
        this.pay = pay;
        this.regDate = regDate;
        this.user = user;
    }
}
