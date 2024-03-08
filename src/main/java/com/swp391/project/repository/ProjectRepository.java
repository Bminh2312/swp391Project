package com.swp391.project.repository;

import com.swp391.project.entity.DesignStyleEntity;
import com.swp391.project.entity.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<ProjectEntity,Integer> {
    Optional<ProjectEntity> findByStatus(String status);

}
