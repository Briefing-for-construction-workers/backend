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
import java.util.Optional;

@Transactional
@Service
@RequiredArgsConstructor
public class PostLikeServiceImpl implements PostLikeService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;

    @Override
    public String likePost(PostLikeReq postLikeReq) {
        User user = userRepository.findById(postLikeReq.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("user doesn't exist"));

        Post post = postRepository.findById(postLikeReq.getPostId())
                .orElseThrow(() -> new IllegalArgumentException("post doesn't exist"));

        Optional<PostLike> postLikeOptional = postLikeRepository.findByUserAndPost(user, post);

        System.out.println(postLikeOptional.isPresent());

        if(postLikeOptional.isPresent()) { //이미 좋아요함
            //삭제된적 없음 = 좋아요 취소 가능
            if(!postLikeOptional.get().isDeleted()) {
                postLikeRepository.delete(postLikeOptional.get());
                return "좋아요 취소";
            } else { //삭제된적 있음 = 다시 좋아요 가능
                System.out.println("좋아요 재등록");
                postLikeRepository.reSave(postLikeOptional.get().getId());
                return "좋아요 재등록";
            }
        } else { //좋아요 처음함
            Date currentDate = new Date();
            Timestamp timestamp = new Timestamp(currentDate.getTime());

            PostLike postLike = PostLike.builder()
                    .createdAt(timestamp)
                    .user(user)
                    .post(post)
                    .build();

            postLikeRepository.save(postLike);

            return "좋아요 등록";
        }

    }

}
