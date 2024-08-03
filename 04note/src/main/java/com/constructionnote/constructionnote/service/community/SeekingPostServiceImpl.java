package com.constructionnote.constructionnote.service.community;

import com.constructionnote.constructionnote.api.request.community.SeekingPostReq;
import com.constructionnote.constructionnote.component.DateProcess;
import com.constructionnote.constructionnote.component.GeoUtils;
import com.constructionnote.constructionnote.dto.community.PostDto;
import com.constructionnote.constructionnote.entity.*;
import com.constructionnote.constructionnote.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class SeekingPostServiceImpl implements SeekingPostService {
    static final int PAGE_SIZE = 10;

    private final UserRepository userRepository;
    private final ConstructionRepository constructionRepository;
    private final PostRepository postRepository;
    private final SeekingPostRepository seekingPostRepository;
    private final AddressRepository addressRepository;

    private final DateProcess dateProcess;
    private final GeoUtils geoUtils;

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
                .activated(true)
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

        return getPostDtoList(seekingPostList);
    }

    @Override
    public List<PostDto> viewSeekingPostsByFilter(Integer page, String fullCode, Double distance, String keyword, String state) {
        Address address = addressRepository.findById(fullCode)
                .orElseThrow(() -> new IllegalArgumentException("addressCode doesn't exist"));

        double[] boundingBox = geoUtils.getBoundingBox(address.getLat(), address.getLng(), distance);
        List<String> nearbyAddressCodes = addressRepository.getNearbyAddressCodeByBoundingBox(address.getLat(), address.getLng(), boundingBox[0], boundingBox[1], boundingBox[2], boundingBox[3]);

        PageRequest pageRequest = PageRequest.of(page, PAGE_SIZE);
        List<SeekingPost> seekingPostList = seekingPostRepository.findByAddressCodeAndKeyword(pageRequest, nearbyAddressCodes, keyword, state);

        return getPostDtoList(seekingPostList);
    }

    private List<PostDto> getPostDtoList(List<SeekingPost> seekingPostList) {
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
                    .postType("seeking")
                    .state(seekingPost.isActivated())
                    .title(seekingPost.getTitle())
                    .skills(skills)
                    .level(seekingPost.getUser().getLevel())
                    .relativeTime(relativeTime)
                    .build();

            postDtoList.add(postDto);
        }

        return postDtoList;
    }
}
