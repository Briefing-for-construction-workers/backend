package com.constructionnote.constructionnote.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Construction {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "construction_id")
    private Long id;
    @Column(nullable = false)
    private String kind;
    @Column(nullable = false)
    private Date timeBegin;
    @Column(nullable = false)
    private Date timeEnd;
    @Column(nullable = false)
    private String city;
    @Column(nullable = false)
    private String district;
    @Column(nullable = false)
    private String dong;
    private String workSiteDescription;
    private String memo;

    @OneToMany(mappedBy = "construction", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ConstructionUser> constructionUserList = new ArrayList<>();

    @Builder
    public Construction(String kind, Date timeBegin, Date timeEnd, String city, String district, String dong, String workSiteDescription, String memo) {
        this.kind = kind;
        this.timeBegin = timeBegin;
        this.timeEnd = timeEnd;
        this.city = city;
        this.district = district;
        this.dong = dong;
        this.workSiteDescription = workSiteDescription;
        this.memo = memo;
    }

    public void addConstructionUser(ConstructionUser constructionUser) {
        this.constructionUserList.add(constructionUser);
    }
}
