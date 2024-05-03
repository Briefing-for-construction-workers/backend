package com.constructionnote.constructionnote.api.request.community;

import lombok.Getter;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Getter
public class HiringPostReq {
    private String userId;
    private String title;
    private Date date;
    private String location;
    private String level;
    private List<String> skills  = new ArrayList<>();
    private Integer pay;
    private String content;
}
