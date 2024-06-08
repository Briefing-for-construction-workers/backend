package com.constructionnote.constructionnote.repository;

import com.constructionnote.constructionnote.entity.Post;
import com.constructionnote.constructionnote.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long>, PostCustomRepository {
    List<Post> findTop5ByOrderByCreatedAtDesc();
    List<Post> findByUserOrderByCreatedAtDesc(User user);
}
