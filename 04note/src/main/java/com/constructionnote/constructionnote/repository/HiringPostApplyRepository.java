package com.constructionnote.constructionnote.repository;

import com.constructionnote.constructionnote.entity.HiringPost;
import com.constructionnote.constructionnote.entity.HiringPostApply;
import com.constructionnote.constructionnote.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HiringPostApplyRepository extends JpaRepository<HiringPostApply, Long> {
    HiringPostApply findByUserAndHiringPost(User user, HiringPost hiringPost);
}
