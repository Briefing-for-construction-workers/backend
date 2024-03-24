package com.constructionnote.constructionnote.entity;

import jakarta.persistence.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Construction {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "construction_id")
    private Long id;
    private Date timeBegin;
    private Date timeEnd;
    private String city;
    private String district;
    private String dong;
    private String income;
    private String account;

    @OneToMany(mappedBy = "construction")
    private List<ConstructionUser> constructionUserList = new ArrayList<>();
}
