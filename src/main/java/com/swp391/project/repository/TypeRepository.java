package com.swp391.project.repository;

import com.swp391.project.entity.RoomEntity;
import com.swp391.project.entity.TypeProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeRepository extends JpaRepository<TypeProjectEntity,Integer> {
}
