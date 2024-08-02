package com.constructionnote.constructionnote.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.sql.Timestamp;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("seeking")
@OnDelete(action = OnDeleteAction.CASCADE)
@Entity
public class SeekingPost extends Post {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "construction_id")
    private Construction construction;
    private boolean activated;

    @Builder
    public SeekingPost(String title, String content, Timestamp createdAt, User user, boolean activated) {
        setTitle(title);
        setContent(content);
        setCreatedAt(createdAt);
        setUser(user);
        this.activated = activated;
    }

    public void updateSeekingPost(String title, String content) {
        setTitle(title);
        setContent(content);
    }

    public void putConstruction(Construction construction) { this.construction = construction; }
}
