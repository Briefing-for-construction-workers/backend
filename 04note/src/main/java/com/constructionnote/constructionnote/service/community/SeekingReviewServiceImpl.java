package com.constructionnote.constructionnote.service.community;

import com.constructionnote.constructionnote.api.request.community.ReviewReq;
import com.constructionnote.constructionnote.api.response.community.ReviewRes;
import com.constructionnote.constructionnote.component.DateProcess;
import com.constructionnote.constructionnote.entity.*;
import com.constructionnote.constructionnote.repository.PostRepository;
import com.constructionnote.constructionnote.repository.ReviewRepository;
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
public class SeekingReviewServiceImpl implements SeekingReviewService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final ReviewRepository reviewRepository;
    private final DateProcess dateProcess;

    @Override
    public Long createSeekingReview(ReviewReq seekingReviewReq) {
        User reviewer = userRepository.findById(seekingReviewReq.getReviewerId())
                .orElseThrow(() -> new IllegalArgumentException("reviewer doesn't exist"));

        User reviewee = userRepository.findById(seekingReviewReq.getRevieweeId())
                .orElseThrow(() -> new IllegalArgumentException("user doesn't exist"));

        Post post = postRepository.findById(seekingReviewReq.getPostId())
                .orElseThrow(() -> new IllegalArgumentException("post doesn't exist"));

        Date currentDate = new Date();
        Timestamp timestamp = new Timestamp(currentDate.getTime());

        Review seekingReview = Review.builder()
                .content(seekingReviewReq.getContent())
                .rate(seekingReviewReq.getRate())
                .createdAt(timestamp)
                .reviewer(reviewer)
                .reviewee(reviewee)
                .type(PostType.SEEKING)
                .post(post)
                .build();

        reviewRepository.save(seekingReview);

        return seekingReview.getId();
    }

    @Override
    public void updateSeekingReview(Long reviewId, ReviewReq seekingReviewReq) {
        Review seekingReview = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("review doesn't exist"));

        seekingReview.updateReview(seekingReviewReq.getContent());

        reviewRepository.save(seekingReview);
    }

    @Override
    public void deleteSeekingReview(Long reviewId) {
        reviewRepository.deleteById(reviewId);
    }

    @Override
    public List<ReviewRes> viewSeekingReviewList(String userId) {
        List<Review> seekingReviewList = reviewRepository.findReviewsByRevieweeAndPostTypeSeeking(userId);

        List<ReviewRes> seekingReviewResList = new ArrayList<>();
        for(Review seekingReview : seekingReviewList) {
            SeekingPost seekingPost = (SeekingPost)seekingReview.getPost();

            List<UserSkill> userSkillList = seekingPost.getUser().getUserSkillList();
            List<String> skills  = new ArrayList<>();
            for(UserSkill userSkill : userSkillList) {
                skills.add(userSkill.getSkill().getName());
            }

            String relativeTime = dateProcess.convertToRelativeTime(seekingReview.getCreatedAt());

            ReviewRes seekingReviewRes = ReviewRes.builder()
                    .reviewId(seekingReview.getId())
                    .skills(skills)
                    .rate(seekingReview.getRate())
                    .content(seekingReview.getContent())
                    .relativeTime(relativeTime)
                    .build();

            seekingReviewResList.add(seekingReviewRes);
        }

        return seekingReviewResList;
    }
}
