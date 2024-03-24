package com.swp391.project.repository;

import com.swp391.project.entity.RawMaterialEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RawMaterialRepository extends JpaRepository<RawMaterialEntity,Integer> {
    long countByStatus(String status);
}
