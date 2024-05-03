package com.constructionnote.constructionnote.dto.post;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HiringPostDto {
    private String title;
    private List<String> skills  = new ArrayList<>();
    private String location;
    private Date date;
    private String level;
    private Integer pay;
    private String content;

    @Builder
    public HiringPostDto(String title, List<String> skills, String location, Date date, String level, Integer pay, String content) {
        this.title = title;
        this.skills = skills;
        this.location = location;
        this.date = date;
        this.level = level;
        this.pay = pay;
        this.content = content;
    }
}
