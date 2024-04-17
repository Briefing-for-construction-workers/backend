package com.constructionnote.constructionnote.api.request;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class UserSignupReq {
    private String userId;
    private String nickname;
    private String address;
    private String level;
    private List<String> skills  = new ArrayList<>();
}
