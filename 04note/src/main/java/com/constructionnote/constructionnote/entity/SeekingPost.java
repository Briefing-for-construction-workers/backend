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
    @ManyToOne
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
