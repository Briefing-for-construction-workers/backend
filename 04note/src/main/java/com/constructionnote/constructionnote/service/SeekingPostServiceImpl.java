package com.constructionnote.constructionnote.service;

import com.constructionnote.constructionnote.api.request.SeekingPostReq;
import com.constructionnote.constructionnote.entity.SeekingPost;
import com.constructionnote.constructionnote.entity.User;
import com.constructionnote.constructionnote.repository.SeekingPostRepository;
import com.constructionnote.constructionnote.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;

@Transactional
@Service
@RequiredArgsConstructor
public class SeekingPostServiceImpl implements SeekingPostService {
    private final UserRepository userRepository;
    private final SeekingPostRepository seekingPostRepository;

    @Override
    public Long registerSeekingPost(SeekingPostReq seekingPostReq) {
        User user = userRepository.findById(seekingPostReq.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("user doesn't exist"));

        Date currentDate = new Date();
        Timestamp timestamp = new Timestamp(currentDate.getTime());

        SeekingPost seekingPost = SeekingPost.builder()
                .title(seekingPostReq.getTitle())
                .content(seekingPostReq.getContent())
                .pay(seekingPostReq.getPay())
                .regDate(timestamp)
                .user(user)
                .build();

        seekingPostRepository.save(seekingPost);
        return seekingPost.getId();
    }
}
