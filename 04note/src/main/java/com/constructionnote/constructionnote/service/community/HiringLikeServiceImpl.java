package com.constructionnote.constructionnote.service.community;

import com.constructionnote.constructionnote.api.request.community.PostLikeReq;
import com.constructionnote.constructionnote.entity.Post;
import com.constructionnote.constructionnote.entity.PostLike;
import com.constructionnote.constructionnote.entity.User;
import com.constructionnote.constructionnote.repository.PostLikeRepository;
import com.constructionnote.constructionnote.repository.PostRepository;
import com.constructionnote.constructionnote.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;

@Transactional
@Service
@RequiredArgsConstructor
public class HiringLikeServiceImpl implements HiringLikeService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;

    @Override
    public Long likeHiringPost(PostLikeReq hiringPostLikeReq) {
        User user = userRepository.findById(hiringPostLikeReq.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("user doesn't exist"));

        Post post = postRepository.findById(hiringPostLikeReq.getPostId())
                .orElseThrow(() -> new IllegalArgumentException("post doesn't exist"));

        Date currentDate = new Date();
        Timestamp timestamp = new Timestamp(currentDate.getTime());

        PostLike postLike = PostLike.builder()
                .createdAt(timestamp)
                .user(user)
                .post(post)
                .build();

        postLikeRepository.save(postLike);

        return postLike.getId();
    }
}
