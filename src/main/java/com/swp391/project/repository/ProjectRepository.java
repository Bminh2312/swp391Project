package com.swp391.project.repository;

import com.swp391.project.entity.DesignStyleEntity;
import com.swp391.project.entity.ProductEntity;
import com.swp391.project.entity.ProjectEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<ProjectEntity,Integer> {
    List<ProjectEntity> findByStatus(String status);

    List<ProjectEntity> findByStatusAndUserId(String status, int userId);

    Optional<ProjectEntity> findById(int id);

    Optional<List<ProjectEntity>> findByIsSample(boolean isSample);

    Page<ProjectEntity> findAllByStatus(String type, Pageable pageable);

    Page<ProjectEntity> findAllByStatusAndDesignStypeAndType(String status, String designStype, String type, Pageable pageable);

    Page<ProjectEntity> findAllByStatusAndUserId( String status, int userId, Pageable pageable);

    Page<ProjectEntity> findAllByUserId(int userId, Pageable pageable);

}
