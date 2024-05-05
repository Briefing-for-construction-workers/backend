package com.constructionnote.constructionnote.service.community;

import com.constructionnote.constructionnote.api.request.community.HiringPostApplyReq;
import com.constructionnote.constructionnote.api.request.community.HiringPostLikeReq;
import com.constructionnote.constructionnote.api.request.community.HiringPostReq;
import com.constructionnote.constructionnote.api.response.community.HiringPostDetailRes;
import com.constructionnote.constructionnote.component.ImageFileStore;
import com.constructionnote.constructionnote.dto.community.HiringPostDto;
import com.constructionnote.constructionnote.dto.user.ProfileDto;
import com.constructionnote.constructionnote.entity.*;
import com.constructionnote.constructionnote.repository.*;
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
    private final HiringPostApplyRepository hiringPostApplyRepository;
    private final SkillRepository skillRepository;
    private final ImageFileStore imageFileStore;

    @Override
    public Long registerHiringPost(HiringPostReq hiringPostReq) {
        User user = userRepository.findById(hiringPostReq.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("user doesn't exist"));

        Date currentDate = new Date();
        Timestamp timestamp = new Timestamp(currentDate.getTime());

        HiringPost hiringPost = HiringPost.builder1()
                .title(hiringPostReq.getTitle())
                .date(hiringPostReq.getDate())
                .location(hiringPostReq.getLocation())
                .level(hiringPostReq.getLevel())
                .pay(hiringPostReq.getPay())
                .content(hiringPostReq.getContent())
                .createdAt(timestamp)
                .state(false)
                .user(user)
                .build1();

        if(hiringPostReq.getSkills() != null) {
            for(String skillName : hiringPostReq.getSkills()) {
                Skill skill = skillRepository.findByName(skillName).orElse(null);

                if(skill == null) {
                    Skill skillTmp = Skill.builder()
                            .name(skillName)
                            .build();

                    skill = skillRepository.save(skillTmp);
                }

                PostSkill postSkill = PostSkill.builder()
                        .hiringPost(hiringPost)
                        .skill(skill)
                        .build();

                hiringPost.addPostSkill(postSkill);
            }
        }

        hiringPostRepository.save(hiringPost);
        return hiringPost.getId();
    }

    @Override
    public HiringPostDetailRes viewHiringPostById(Long hiringPostId) throws Exception {
        HiringPost hiringPost = hiringPostRepository.findById(hiringPostId)
                .orElseThrow(() -> new IllegalArgumentException("hiringPost doesn't exist"));

        String profileUrl = hiringPost.getUser().getProfile().getImageUrl();

        byte[] image = null;
        if(profileUrl != null) {
            image = imageFileStore.getFile(profileUrl);
        }

        List<UserSkill> userSkillList = hiringPost.getUser().getUserSkillList();
        List<String> skills  = new ArrayList<>();
        for(UserSkill userSkill : userSkillList) {
            skills.add(userSkill.getSkill().getName());
        }

        ProfileDto profileDto = ProfileDto.builder()
                .nickname(hiringPost.getUser().getProfile().getNickname())
                .image(image)
                .skills(skills)
                .build();

        List<PostSkill> postSkillList = hiringPost.getPostSkillList();
        skills  = new ArrayList<>();
        for(PostSkill postSkill : postSkillList) {
            skills.add(postSkill.getSkill().getName());
        }

        HiringPostDto hiringPostDto = HiringPostDto.builder()
                .title(hiringPost.getTitle())
                .skills(skills)
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
    public void updateHiringPost(Long hiringPostId, HiringPostReq hiringPostReq) {
        User user = userRepository.findById(hiringPostReq.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("user doesn't exist"));

        HiringPost hiringPost =  hiringPostRepository.findById(hiringPostId)
                .orElseThrow(() -> new IllegalArgumentException("hiringPost doesn't exist"));;

        HiringPost newHiringPost = HiringPost.builder2()
                .postId(hiringPostId)
                .title(hiringPostReq.getTitle())
                .date(hiringPostReq.getDate())
                .location(hiringPostReq.getLocation())
                .level(hiringPostReq.getLevel())
                .pay(hiringPostReq.getPay())
                .content(hiringPostReq.getContent())
                .createdAt(hiringPost.getCreatedAt())
                .state(hiringPost.isState())
                .user(user)
                .build2();

        if(hiringPostReq.getSkills() != null) {
            for(String skillName : hiringPostReq.getSkills()) {
                Skill skill = skillRepository.findByName(skillName).orElse(null);

                if(skill == null) {
                    Skill skillTmp = Skill.builder()
                            .name(skillName)
                            .build();

                    skill = skillRepository.save(skillTmp);
                }

                PostSkill postSkill = PostSkill.builder()
                        .hiringPost(newHiringPost)
                        .skill(skill)
                        .build();

                newHiringPost.addPostSkill(postSkill);
            }
        }

        hiringPostRepository.save(newHiringPost);
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

    @Override
    public Long applyHiringPost(HiringPostApplyReq hiringPostApplyReq) {
        User user = userRepository.findById(hiringPostApplyReq.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("user doesn't exist"));

        HiringPost hiringPost = hiringPostRepository.findById(hiringPostApplyReq.getHiringPostId())
                .orElseThrow(() -> new IllegalArgumentException("hiringPost doesn't exist"));

        Date currentDate = new Date();
        Timestamp timestamp = new Timestamp(currentDate.getTime());

        HiringPostApply hiringPostApply = HiringPostApply.builder()
                .isHired(false)
                .createdAt(timestamp)
                .user(user)
                .hiringPost(hiringPost)
                .build();

        hiringPostApplyRepository.save(hiringPostApply);

        return hiringPostApply.getId();
    }

}
