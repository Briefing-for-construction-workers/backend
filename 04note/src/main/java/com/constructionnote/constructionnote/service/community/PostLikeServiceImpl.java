package com.constructionnote.constructionnote.service.community;

import com.constructionnote.constructionnote.api.request.community.PostLikeReq;
import com.constructionnote.constructionnote.component.DateProcess;
import com.constructionnote.constructionnote.dto.community.PostDto;
import com.constructionnote.constructionnote.entity.*;
import com.constructionnote.constructionnote.repository.PostLikeRepository;
import com.constructionnote.constructionnote.repository.PostRepository;
import com.constructionnote.constructionnote.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
@RequiredArgsConstructor
public class PostLikeServiceImpl implements PostLikeService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;

    private final DateProcess dateProcess;

    @Override
    public String likePost(PostLikeReq postLikeReq) {
        User user = userRepository.findById(postLikeReq.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("user doesn't exist"));

        Post post = postRepository.findById(postLikeReq.getPostId())
                .orElseThrow(() -> new IllegalArgumentException("post doesn't exist"));

        Optional<PostLike> postLikeOptional = postLikeRepository.findByUserAndPost(user, post);

        if(postLikeOptional.isPresent()) { //이미 좋아요함
            //삭제된적 없음 = 좋아요 취소 가능
            if(!postLikeOptional.get().isDeleted()) {
                postLikeRepository.delete(postLikeOptional.get());
                return "좋아요 취소";
            } else { //삭제된적 있음 = 다시 좋아요 가능
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

    @Override
    public List<PostDto> viewLikedHiringPostsByUserId(String userId) {
        List<HiringPost> hiringPostList = postLikeRepository.findLikedHiringPostsByUserId(userId);

        List<PostDto> postDtoList = new ArrayList<>();
        for(HiringPost hiringPost : hiringPostList) {
            String relativeTime = dateProcess.convertToRelativeTime(hiringPost.getCreatedAt());

            List<PostSkill> postSkillList = hiringPost.getPostSkillList();
            List<String> skills  = new ArrayList<>();
            for(PostSkill postSkill : postSkillList) {
                skills.add(postSkill.getSkill().getName());
            }

            PostDto postDto = PostDto.builder()
                    .postId(hiringPost.getId())
                    .postType("구인")
                    .state(hiringPost.isState())
                    .title(hiringPost.getTitle())
                    .skills(skills)
                    .level(hiringPost.getLevel())
                    .date(hiringPost.getDate())
                    .relativeTime(relativeTime)
                    .build();

            postDtoList.add(postDto);
        }

        return postDtoList;
    }

    @Override
    public List<PostDto> viewLikedSeekingPostsByUserId(String userId) {
        List<SeekingPost> seekingPostList = postLikeRepository.findLikedSeekingPostsByUserId(userId);

        List<PostDto> postDtoList = new ArrayList<>();
        for(SeekingPost seekingPost : seekingPostList) {
            String relativeTime = dateProcess.convertToRelativeTime(seekingPost.getCreatedAt());

            List<UserSkill> userSkillList = seekingPost.getUser().getUserSkillList();
            List<String> skills  = new ArrayList<>();
            for(UserSkill userSkill : userSkillList) {
                skills.add(userSkill.getSkill().getName());
            }

            PostDto postDto = PostDto.builder()
                    .postId(seekingPost.getId())
                    .postType("구직")
                    .title(seekingPost.getTitle())
                    .skills(skills)
                    .level(seekingPost.getUser().getLevel())
                    .date(null)
                    .relativeTime(relativeTime)
                    .build();

            postDtoList.add(postDto);
        }

        return postDtoList;
    }

}
