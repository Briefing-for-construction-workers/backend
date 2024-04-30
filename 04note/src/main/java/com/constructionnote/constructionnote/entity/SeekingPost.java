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
@DiscriminatorValue("seeking")
public class SeekingPost extends Post {
    @OneToOne
    @JoinColumn(name = "construction_id")
    private Construction construction;

    @Builder
    public SeekingPost(String title, String content, Timestamp createdAt, User user, Construction construction) {
        setTitle(title);
        setContent(content);
        setCreatedAt(createdAt);
        setUser(user);
        this.construction = construction;
    }
}
