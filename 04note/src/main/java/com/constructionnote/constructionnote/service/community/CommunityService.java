package com.constructionnote.constructionnote.service.community;

import com.constructionnote.constructionnote.dto.community.PostDto;

import java.io.IOException;
import java.util.List;

public interface CommunityService {
    List<PostDto> viewMyPostList(String userId);

    List<PostDto> viewPostListByFilter(Integer page, String fullCode, String keyword) throws IOException;
}