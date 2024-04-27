package com.constructionnote.constructionnote.service;

import com.constructionnote.constructionnote.api.request.HiringPostReq;
import com.constructionnote.constructionnote.entity.HiringPost;
import com.constructionnote.constructionnote.entity.User;
import com.constructionnote.constructionnote.repository.HiringPostRepository;
import com.constructionnote.constructionnote.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;

@Transactional
@Service
@RequiredArgsConstructor
public class HiringPostServiceImpl implements HiringPostService {
    private final UserRepository userRepository;
    private final HiringPostRepository hiringPostRepository;

    @Override
    public Long registerHiringPost(HiringPostReq hiringPostReq) {
        User user = userRepository.findById(hiringPostReq.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("user doesn't exist"));

        Date currentDate = new Date();
        Timestamp timestamp = new Timestamp(currentDate.getTime());

        HiringPost hiringPost = HiringPost.builder()
                .title(hiringPostReq.getTitle())
                .date(hiringPostReq.getDate())
                .location(hiringPostReq.getLocation())
                .level(hiringPostReq.getLevel())
                .skill(hiringPostReq.getSkill())
                .pay(hiringPostReq.getPay())
                .content(hiringPostReq.getContent())
                .createdAt(timestamp)
                .state(false)
                .employer(user)
                .build();

        hiringPostRepository.save(hiringPost);
        return hiringPost.getId();
    }
}
