package com.constructionnote.constructionnote.service;

import com.constructionnote.constructionnote.api.request.HiringPostLikeReq;
import com.constructionnote.constructionnote.api.request.HiringPostReq;
import com.constructionnote.constructionnote.api.response.HiringPostDetailRes;
import com.constructionnote.constructionnote.component.ImageFileStore;
import com.constructionnote.constructionnote.dto.user.HiringPostDto;
import com.constructionnote.constructionnote.dto.user.ProfileDto;
import com.constructionnote.constructionnote.entity.HiringLike;
import com.constructionnote.constructionnote.entity.HiringPost;
import com.constructionnote.constructionnote.entity.User;
import com.constructionnote.constructionnote.entity.UserSkill;
import com.constructionnote.constructionnote.repository.HiringLikeRepository;
import com.constructionnote.constructionnote.repository.HiringPostRepository;
import com.constructionnote.constructionnote.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class HiringPostServiceImpl implements HiringPostService {
    private final UserRepository userRepository;
    private final HiringPostRepository hiringPostRepository;
    private final HiringLikeRepository hiringLikeRepository;
    private final ImageFileStore imageFileStore;

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

    @Override
    public HiringPostDetailRes viewHiringPostById(Long hiringPostId) throws Exception {
        HiringPost hiringPost = hiringPostRepository.findById(hiringPostId)
                .orElseThrow(() -> new IllegalArgumentException("hiringPost doesn't exist"));

        byte[] image = imageFileStore.getFile(hiringPost.getEmployer().getProfile().getImageUrl());

        List<UserSkill> userSkillList = hiringPost.getEmployer().getUserSkillList();
        List<String> skills  = new ArrayList<>();
        for(UserSkill userSkill : userSkillList) {
            skills.add(userSkill.getSkill().getName());
        }

        ProfileDto profileDto = ProfileDto.builder()
                .nickname(hiringPost.getEmployer().getProfile().getNickname())
                .image(image)
                .skills(skills)
                .build();

        HiringPostDto hiringPostDto = HiringPostDto.builder()
                .skill(hiringPost.getSkill())
                .location(hiringPost.getLocation())
                .date(hiringPost.getDate())
                .level(hiringPost.getLevel())
                .pay(hiringPost.getPay())
                .content(hiringPost.getContent())
                .build();

        return HiringPostDetailRes.builder()
                .profileDto(profileDto)
                .kind("구인")
                .hiringPostDto(hiringPostDto)
                .build();
    }

    @Override
    public Long likeHiringPost(HiringPostLikeReq hiringPostLikeReq) {
        User user = userRepository.findById(hiringPostLikeReq.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("user doesn't exist"));

        HiringPost hiringPost = hiringPostRepository.findById(hiringPostLikeReq.getHiringPostId())
                .orElseThrow(() -> new IllegalArgumentException("hiringPost doesn't exist"));

        Date currentDate = new Date();
        Timestamp timestamp = new Timestamp(currentDate.getTime());

        HiringLike hiringLike = HiringLike.builder()
                .createdAt(timestamp)
                .user(user)
                .hiringPost(hiringPost)
                .build();

        hiringLikeRepository.save(hiringLike);

        return hiringLike.getId();
    }
}
