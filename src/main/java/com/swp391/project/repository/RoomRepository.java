package com.swp391.project.repository;

import com.swp391.project.entity.ProjectEntity;
import com.swp391.project.entity.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<RoomEntity,Integer> {

    List<RoomEntity> findByProjectRoom(ProjectEntity projectEntity);

    Optional<RoomEntity> findByName(String name);

}
