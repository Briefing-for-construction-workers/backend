package com.constructionnote.constructionnote.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.sql.Timestamp;

@Getter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn
@SQLDelete(sql = "UPDATE post SET deleted = true WHERE post_id = ?")
@SQLRestriction("deleted = false")
public abstract class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    @Setter(AccessLevel.PROTECTED)
    private Long id;
    @Setter(AccessLevel.PROTECTED)
    private String title;
    @Setter(AccessLevel.PROTECTED)
    private String content;
    @Setter(AccessLevel.PROTECTED)
    private Timestamp createdAt;
    private boolean deleted = Boolean.FALSE;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @Setter(AccessLevel.PROTECTED)
    private User user;
}
