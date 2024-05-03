package com.constructionnote.constructionnote.repository;

import com.constructionnote.constructionnote.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findTop5ByOrderByCreatedAtDesc();
}
