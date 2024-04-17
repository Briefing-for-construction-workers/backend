package com.constructionnote.constructionnote.repository;

import com.constructionnote.constructionnote.entity.Skill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SkillRepository extends JpaRepository<Skill, Integer> {
    boolean existsByName(String name);
    Optional<Skill> findByName(String name);
}
