package com.constructionnote.constructionnote.service;

import com.constructionnote.constructionnote.api.request.UserProfileReq;
import com.constructionnote.constructionnote.component.ImageFileStore;
import com.constructionnote.constructionnote.entity.User;
import com.constructionnote.constructionnote.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@Transactional
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ImageFileStore imageFileStore;

    @Override
    public void signUp(String userId) {
        User user = User.builder1()
                .id(userId)
                .build1();

        userRepository.save(user);
    }

    @Override
    public boolean exist(String userId) {
        User user = userRepository.findById(userId).orElse(null);
        return user != null;
    }

    @Override
    public void updateUserProfile(UserProfileReq userProfileReq, MultipartFile image) throws IOException {
        String id = userProfileReq.getUserId();
        String nickname = userProfileReq.getNickname();

        System.out.println("id: " + id);
        System.out.println("nickname: " + nickname);

        String storeFilename = imageFileStore.storeFile(image);

        User user = User.builder2()
                .id(id)
                .nickname(nickname)
                .profileUrl(storeFilename)
                .build2();



        userRepository.save(user);
    }
}
