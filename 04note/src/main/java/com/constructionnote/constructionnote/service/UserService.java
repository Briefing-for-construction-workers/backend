package com.constructionnote.constructionnote.service;

public interface UserService {
    void signUp(String userId);
    boolean exist(String userId);
}
