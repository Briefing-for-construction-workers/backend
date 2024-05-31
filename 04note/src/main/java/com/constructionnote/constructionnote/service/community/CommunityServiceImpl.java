package com.constructionnote.constructionnote.service.community;

import com.constructionnote.constructionnote.component.DateProcess;
import com.constructionnote.constructionnote.dto.community.PostDto;
import com.constructionnote.constructionnote.entity.*;
import com.constructionnote.constructionnote.repository.PostRepository;
import com.constructionnote.constructionnote.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class CommunityServiceImpl implements CommunityService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final DateProcess dateProcess;

    @Override
    public List<PostDto> viewPostList() {
        List<Post> postList = postRepository.findTop5ByOrderByCreatedAtDesc();
        return getPostDtoList(postList);
    }

    @Override
    public List<PostDto> viewMyPostList(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("user doesn't exist"));

        List<Post> postList = postRepository.findByUserOrderByCreatedAtDesc(user);

        return getPostDtoList(postList);
    }

    @Override
    public List<PostDto> viewPostListByFilter(Integer page, String fullCode, String keyword) {
        return null;
    }

    private List<PostDto> getPostDtoList(List<Post> postList) {
        List<PostDto> postDtoList = new ArrayList<>();

        for(Post post : postList) {
            PostDto postDto = null;
            String relativeTime = dateProcess.convertToRelativeTime(post.getCreatedAt());

            if(post instanceof HiringPost) {
                HiringPost hiringPost = (HiringPost)post;

                List<PostSkill> postSkillList = hiringPost.getPostSkillList();
                List<String> skills  = new ArrayList<>();
                for(PostSkill postSkill : postSkillList) {
                    skills.add(postSkill.getSkill().getName());
                }

                postDto = PostDto.builder()
                        .postId(post.getId())
                        .postType("구인")
                        .title(hiringPost.getTitle())
                        .skills(skills)
                        .level(hiringPost.getLevel())
                        .date(hiringPost.getDate())
                        .relativeTime(relativeTime)
                        .build();

            } else if(post instanceof SeekingPost) {
                SeekingPost seekingPost = (SeekingPost)post;

                List<UserSkill> userSkillList = seekingPost.getUser().getUserSkillList();
                List<String> skills  = new ArrayList<>();
                for(UserSkill userSkill : userSkillList) {
                    skills.add(userSkill.getSkill().getName());
                }

                postDto = PostDto.builder()
                        .postId(post.getId())
                        .postType("구직")
                        .title(seekingPost.getTitle())
                        .skills(skills)
                        .level(seekingPost.getUser().getLevel())
                        .date(null)
                        .relativeTime(relativeTime)
                        .build();
            }

            postDtoList.add(postDto);
        }

        return postDtoList;
    }
}
