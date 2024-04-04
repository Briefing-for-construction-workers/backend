package com.constructionnote.constructionnote.service;

import com.constructionnote.constructionnote.entity.User;
import com.constructionnote.constructionnote.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Transactional
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public void signUp(String userId) {
        User user = User.builder()
                .id(userId)
                .build();

        userRepository.save(user);
    }
}
