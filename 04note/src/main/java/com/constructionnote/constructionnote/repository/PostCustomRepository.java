package com.constructionnote.constructionnote.repository;

import com.constructionnote.constructionnote.entity.Post;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostCustomRepository {
    List<Post> findByAddressCodeAndKeyword(Pageable pageable, List<String> addressCodes, String keyword);
}
