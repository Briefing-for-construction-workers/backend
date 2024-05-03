package com.constructionnote.constructionnote.service.community;

import com.constructionnote.constructionnote.api.request.community.HiringReviewReq;
import com.constructionnote.constructionnote.api.response.community.HiringReviewRes;
import com.constructionnote.constructionnote.component.DateProcess;
import com.constructionnote.constructionnote.component.ImageFileStore;
import com.constructionnote.constructionnote.entity.HiringPost;
import com.constructionnote.constructionnote.entity.HiringReview;
import com.constructionnote.constructionnote.entity.PostSkill;
import com.constructionnote.constructionnote.entity.User;
import com.constructionnote.constructionnote.repository.HiringPostRepository;
import com.constructionnote.constructionnote.repository.HiringReviewRepository;
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
public class HiringReviewServiceImpl implements HiringReviewService {
    private final UserRepository userRepository;
    private final HiringPostRepository hiringPostRepository;
    private final HiringReviewRepository hiringReviewRepository;

    private final ImageFileStore imageFileStore;
    private final DateProcess dateProcess;

    @Override
    public Long createHiringReview(HiringReviewReq hiringReviewReq) {
        User reviewer = userRepository.findById(hiringReviewReq.getReviewerId())
                .orElseThrow(() -> new IllegalArgumentException("reviewer doesn't exist"));

        User reviewee = userRepository.findById(hiringReviewReq.getRevieweeId())
                .orElseThrow(() -> new IllegalArgumentException("user doesn't exist"));

        HiringPost hiringPost = hiringPostRepository.findById(hiringReviewReq.getHiringPostId())
                .orElseThrow(() -> new IllegalArgumentException("hiringPost doesn't exist"));

        Date currentDate = new Date();
        Timestamp timestamp = new Timestamp(currentDate.getTime());

        HiringReview hiringReview = HiringReview.builder()
                .content(hiringReviewReq.getContent())
                .createdAt(timestamp)
                .reviewer(reviewer)
                .reviewee(reviewee)
                .hiringPost(hiringPost)
                .build();

        hiringReviewRepository.save(hiringReview);

        return hiringReview.getId();
    }

    @Override
    public List<HiringReviewRes> viewReviewList(String userId) throws Exception {
        User reviewee = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("reviewee doesn't exist"));

        List<HiringReview> hiringReviewList = hiringReviewRepository.findByReviewee(reviewee);

        List<HiringReviewRes> hiringReviewResList = new ArrayList<>();

        for(HiringReview hiringReview : hiringReviewList) {
            //리뷰어 정보
            User reviewer = userRepository.findById(hiringReview.getReviewer().getId())
                    .orElseThrow(() -> new IllegalArgumentException("reviewer doesn't exist"));

            String profileUrl = reviewer.getProfile().getImageUrl();

            byte[] image = null;
            if(profileUrl != null) {
                image = imageFileStore.getFile(profileUrl);
            }

            //게시글 정보
            List<PostSkill> postSkillList = hiringReview.getHiringPost().getPostSkillList();
            List<String> skills  = new ArrayList<>();
            for(PostSkill postSkill : postSkillList) {
                skills.add(postSkill.getSkill().getName());
            }

            String relativeTime = dateProcess.convertToRelativeTime(hiringReview.getCreatedAt());

            HiringReviewRes hiringReviewRes = HiringReviewRes.builder()
                    .nickname(hiringReview.getReviewer().getProfile().getNickname())
                    .image(image)
                    .skills(skills)
                    .content(hiringReview.getContent())
                    .relativeTime(relativeTime)
                    .build();

            hiringReviewResList.add(hiringReviewRes);
        }

        return hiringReviewResList;
    }

    @Override
    public List<HiringReviewRes> viewLimitedReviewList(String userId) throws Exception {
        User reviewee = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("reviewee doesn't exist"));

        List<HiringReview> hiringReviewList = hiringReviewRepository.findTop3ByRevieweeOrderByCreatedAtDesc(reviewee);

        List<HiringReviewRes> hiringReviewResList = new ArrayList<>();

        for(HiringReview hiringReview : hiringReviewList) {
            //리뷰어 정보
            User reviewer = userRepository.findById(hiringReview.getReviewer().getId())
                    .orElseThrow(() -> new IllegalArgumentException("reviewer doesn't exist"));

            String profileUrl = reviewer.getProfile().getImageUrl();

            byte[] image = null;
            if(profileUrl != null) {
                image = imageFileStore.getFile(profileUrl);
            }

            //게시글 정보
            List<PostSkill> postSkillList = hiringReview.getHiringPost().getPostSkillList();
            List<String> skills  = new ArrayList<>();
            for(PostSkill postSkill : postSkillList) {
                skills.add(postSkill.getSkill().getName());
            }

            String relativeTime = dateProcess.convertToRelativeTime(hiringReview.getCreatedAt());

            HiringReviewRes hiringReviewRes = HiringReviewRes.builder()
                    .nickname(hiringReview.getReviewer().getProfile().getNickname())
                    .image(image)
                    .skills(skills)
                    .content(hiringReview.getContent())
                    .relativeTime(relativeTime)
                    .build();

            hiringReviewResList.add(hiringReviewRes);
        }

        return hiringReviewResList;
    }

}
