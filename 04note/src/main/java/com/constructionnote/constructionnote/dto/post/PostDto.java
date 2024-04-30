package com.constructionnote.constructionnote.dto.post;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostDto {
    private String postType;
    private String title;
    private String skill;
    private String level;
    private Date date;
    private String relativeTime;

    @Builder
    public PostDto(String postType, String title, String skill, String level, Date date, String relativeTime) {
        this.postType = postType;
        this.title = title;
        this.skill = skill;
        this.level = level;
        this.date = date;
        this.relativeTime = relativeTime;
    }
}
