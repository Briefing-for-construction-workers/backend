package com.constructionnote.constructionnote.service.community;

import com.constructionnote.constructionnote.api.request.community.HiringReviewReq;
import com.constructionnote.constructionnote.entity.HiringPost;
import com.constructionnote.constructionnote.entity.HiringReview;
import com.constructionnote.constructionnote.entity.User;
import com.constructionnote.constructionnote.repository.HiringPostRepository;
import com.constructionnote.constructionnote.repository.HiringReviewRepository;
import com.constructionnote.constructionnote.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;

@Transactional
@Service
@RequiredArgsConstructor
public class HiringReviewServiceImpl implements HiringReviewService {
    private final UserRepository userRepository;
    private final HiringPostRepository hiringPostRepository;
    private final HiringReviewRepository hiringReviewRepository;
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
}
