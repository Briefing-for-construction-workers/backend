package com.constructionnote.constructionnote.dto.user;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HiringPostDto {
    private String skill;
    private String location;
    private Date date;
    private String level;
    private Integer pay;
    private String content;

    @Builder
    public HiringPostDto(String skill, String location, Date date, String level, Integer pay, String content) {
        this.skill = skill;
        this.location = location;
        this.date = date;
        this.level = level;
        this.pay = pay;
        this.content = content;
    }
}