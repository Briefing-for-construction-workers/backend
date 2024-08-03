package com.constructionnote.constructionnote.repository;

import com.constructionnote.constructionnote.entity.SeekingPost;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SeekingPostCustomRepository {
    List<SeekingPost> findByAddressCodeAndKeyword(Pageable pageable, List<String> addressCodes, String keyword, String state);
}
