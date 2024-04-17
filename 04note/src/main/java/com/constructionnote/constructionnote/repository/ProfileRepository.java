package com.constructionnote.constructionnote.repository;

import com.constructionnote.constructionnote.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
}
