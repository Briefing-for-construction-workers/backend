package com.constructionnote.constructionnote.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;

@Getter
@Entity
public class Address {
    @Id
    private String addressCode;
    private String sido;
    private String sigungu;
    private String eubmyeondong;
    private Double lat;
    private Double lng;
}
