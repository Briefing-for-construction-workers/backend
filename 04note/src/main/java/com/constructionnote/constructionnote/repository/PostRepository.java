package com.constructionnote.constructionnote.repository;

import com.constructionnote.constructionnote.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long>, PostCustomRepository {
}
