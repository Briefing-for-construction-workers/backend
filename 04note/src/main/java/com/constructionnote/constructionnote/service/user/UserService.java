package com.constructionnote.constructionnote.service.user;

import com.constructionnote.constructionnote.api.request.user.UserProfileReq;
import com.constructionnote.constructionnote.api.request.user.UserSignupReq;
import com.constructionnote.constructionnote.api.response.user.UserProfileRes;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UserService {
    void signUp(UserSignupReq userSignupReq, MultipartFile image) throws Exception;
    boolean exist(String userId);

    void updateUserProfile(UserProfileReq userProfileReq, MultipartFile image) throws Exception;
    UserProfileRes getUserProfile(String userId) throws Exception;
}
