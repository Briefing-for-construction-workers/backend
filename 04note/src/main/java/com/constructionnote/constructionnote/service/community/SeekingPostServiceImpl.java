package com.constructionnote.constructionnote.service.community;

import com.constructionnote.constructionnote.api.request.community.SeekingPostReq;
import com.constructionnote.constructionnote.component.DateProcess;
import com.constructionnote.constructionnote.dto.community.PostDto;
import com.constructionnote.constructionnote.entity.Construction;
import com.constructionnote.constructionnote.entity.SeekingPost;
import com.constructionnote.constructionnote.entity.User;
import com.constructionnote.constructionnote.entity.UserSkill;
import com.constructionnote.constructionnote.repository.ConstructionRepository;
import com.constructionnote.constructionnote.repository.PostRepository;
import com.constructionnote.constructionnote.repository.SeekingPostRepository;
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
public class SeekingPostServiceImpl implements SeekingPostService {
    private final UserRepository userRepository;
    private final ConstructionRepository constructionRepository;
    private final PostRepository postRepository;
    private final SeekingPostRepository seekingPostRepository;

    private final DateProcess dateProcess;

    @Override
    public Long registerSeekingPost(SeekingPostReq seekingPostReq) {
        User user = userRepository.findById(seekingPostReq.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("user doesn't exist"));

        Construction construction = constructionRepository.findById(seekingPostReq.getConstructionId())
                .orElseThrow(() -> new IllegalArgumentException("construction doesn't exist"));

        Date currentDate = new Date();
        Timestamp timestamp = new Timestamp(currentDate.getTime());

        SeekingPost seekingPost = SeekingPost.builder()
                .title(seekingPostReq.getTitle())
                .content(seekingPostReq.getContent())
                .createdAt(timestamp)
                .user(user)
                .build();

        seekingPost.putConstruction(construction);

        seekingPostRepository.save(seekingPost);
        return seekingPost.getId();
    }

    @Override
    public void updateSeekingPost(Long postId, SeekingPostReq seekingPostReq) {
        SeekingPost seekingPost = seekingPostRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("seekingPost doesn't exist"));

        seekingPost.updateSeekingPost(
                seekingPostReq.getTitle()
                ,seekingPostReq.getContent()
        );

        Construction construction = constructionRepository.findById(seekingPostReq.getConstructionId())
                .orElseThrow(() -> new IllegalArgumentException("construction doesn't exist"));

        seekingPost.putConstruction(construction);

        seekingPostRepository.save(seekingPost);
    }

    @Override
    public void deleteSeekingPost(Long postId) {
        postRepository.deleteById(postId);
    }

    @Override
    public List<PostDto> viewSeekingPostByUserId(String userId) {
        List<SeekingPost> seekingPostList = seekingPostRepository.findByUserIdOrderByCreatedAtDesc(userId);

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
