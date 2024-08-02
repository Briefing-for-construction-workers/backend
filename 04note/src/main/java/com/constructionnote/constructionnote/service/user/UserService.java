package com.constructionnote.constructionnote.service.user;

import com.constructionnote.constructionnote.api.request.user.UserReq;
import com.constructionnote.constructionnote.api.response.user.UserProfileRes;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
    void signUp(UserReq userReq, MultipartFile image) throws Exception;
    boolean exist(String userId);

    void updateUserProfile(UserReq userReq, MultipartFile image) throws Exception;
    UserProfileRes getUserProfile(String userId) throws Exception;

    void updateSeekingState(String userId);
}
