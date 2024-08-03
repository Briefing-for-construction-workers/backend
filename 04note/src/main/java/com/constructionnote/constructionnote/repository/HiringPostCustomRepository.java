package com.constructionnote.constructionnote.repository;

import com.constructionnote.constructionnote.entity.HiringPost;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface HiringPostCustomRepository {
    List<HiringPost> findByAddressCodeAndKeyword(Pageable pageable, List<String> addressCodes, String keyword, String state);
}
