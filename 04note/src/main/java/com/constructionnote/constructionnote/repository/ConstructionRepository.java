package com.constructionnote.constructionnote.repository;

import com.constructionnote.constructionnote.entity.Construction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConstructionRepository extends JpaRepository<Construction, Long> {
}
