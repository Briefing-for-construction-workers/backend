package com.constructionnote.constructionnote.service;

import com.constructionnote.constructionnote.api.request.UserProfileReq;
import com.constructionnote.constructionnote.api.response.UserProfileRes;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UserService {
    void signUp(String userId);
    boolean exist(String userId);

    void updateUserProfile(UserProfileReq userProfileReq, MultipartFile image) throws IOException;
    UserProfileRes getUserProfile(String userId) throws Exception;
}
