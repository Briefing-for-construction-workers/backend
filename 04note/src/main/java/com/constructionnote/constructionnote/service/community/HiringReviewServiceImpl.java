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
public class HiringReviewServiceImpl implements HiringReviewService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final ReviewRepository reviewRepository;
    private final DateProcess dateProcess;

    @Override
    public Long createHiringReview(ReviewReq hiringReviewReq) {
        User reviewer = userRepository.findById(hiringReviewReq.getReviewerId())
                .orElseThrow(() -> new IllegalArgumentException("reviewer doesn't exist"));

        User reviewee = userRepository.findById(hiringReviewReq.getRevieweeId())
                .orElseThrow(() -> new IllegalArgumentException("user doesn't exist"));

        Post post = postRepository.findById(hiringReviewReq.getPostId())
                .orElseThrow(() -> new IllegalArgumentException("post doesn't exist"));

        Date currentDate = new Date();
        Timestamp timestamp = new Timestamp(currentDate.getTime());

        Review hiringReview = Review.builder()
                .content(hiringReviewReq.getContent())
                .rate(hiringReviewReq.getRate())
                .createdAt(timestamp)
                .reviewer(reviewer)
                .reviewee(reviewee)
                .type(PostType.HIRING)
                .post(post)
                .build();

        reviewRepository.save(hiringReview);

        return hiringReview.getId();
    }

    @Override
    public void updateHiringReview(Long reviewId, ReviewReq hiringReviewReq) {
        Review hiringReview = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("review doesn't exist"));

        hiringReview.updateReview(hiringReviewReq.getContent());

        reviewRepository.save(hiringReview);
    }

    @Override
    public List<ReviewRes> viewHiringReviewList(String userId) {
        List<Review> hiringReviewList = reviewRepository.findReviewsByRevieweeAndPostTypeHiring(userId);

        List<ReviewRes> hiringReviewResList = new ArrayList<>();
        for(Review hiringReview : hiringReviewList) {
            //게시글 정보
            HiringPost hiringPost = (HiringPost)hiringReview.getPost();

            List<PostSkill> postSkillList = hiringPost.getPostSkillList();
            List<String> skills  = new ArrayList<>();
            for(PostSkill postSkill : postSkillList) {
                skills.add(postSkill.getSkill().getName());
            }

            String relativeTime = dateProcess.convertToRelativeTime(hiringReview.getCreatedAt());

            ReviewRes hiringReviewRes = ReviewRes.builder()
                    .reviewId(hiringReview.getId())
                    .skills(skills)
                    .rate(hiringReview.getRate())
                    .content(hiringReview.getContent())
                    .relativeTime(relativeTime)
                    .build();

            hiringReviewResList.add(hiringReviewRes);
        }

        return hiringReviewResList;
    }

    @Override
    public void deleteHiringReview(Long reviewId) {
        reviewRepository.deleteById(reviewId);
    }

}
