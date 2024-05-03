package com.constructionnote.constructionnote.dto.community;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostDto {
    private String postType;
    private String title;
    private List<String> skills  = new ArrayList<>();
    private String level;
    private Date date;
    private String relativeTime;

    @Builder
    public PostDto(String postType, String title, List<String> skills, String level, Date date, String relativeTime) {
        this.postType = postType;
        this.title = title;
        this.skills = skills;
        this.level = level;
        this.date = date;
        this.relativeTime = relativeTime;
    }
}
