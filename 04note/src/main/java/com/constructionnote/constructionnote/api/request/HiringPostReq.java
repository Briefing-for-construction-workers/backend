package com.constructionnote.constructionnote.api.request;

import lombok.Getter;

import java.sql.Date;

@Getter
public class HiringPostReq {
    private String userId;
    private String title;
    private Date date;
    private String location;
    private String level;
    private String skill;
    private Integer pay;
    private String content;
}
