package com.swp391.project.repository;

import com.swp391.project.entity.DesignStyleEntity;
import com.swp391.project.entity.ProductEntity;
import com.swp391.project.entity.ProjectEntity;
import com.swp391.project.entity.TypeProjectEntity;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<ProjectEntity,Integer>, JpaSpecificationExecutor<ProjectEntity> {
    List<ProjectEntity> findByStatus(String status);

    List<ProjectEntity> findByStatusAndUserId(String status, int userId);

    Optional<ProjectEntity> findById(int id);

    Optional<List<ProjectEntity>> findByIsSample(boolean isSample);

    Page<ProjectEntity> findAllByStatus(String type, Pageable pageable);

    Page<ProjectEntity> findAllByTypeProject_Id(int styleId,Pageable  pageable);

    Page<ProjectEntity> findAllByStatusAndUserId( String status, int userId, Pageable pageable);

    Page<ProjectEntity> findAllByUserId(int userId, Pageable pageable);

    Page<ProjectEntity> findAllByDesignStyleId(int designStyleId, Pageable pageable);

    default Page<ProjectEntity> findAllByStatusAndTypeAndDesignStyle(String status, int designStyleId, int typeId, Pageable pageable) {
        Specification<ProjectEntity> spec = Specification.where(null);

        if (status != null || typeId != 0 || designStyleId != 0) {
            spec = spec.and((root, query, criteriaBuilder) -> {
                Predicate predicate = criteriaBuilder.conjunction();
                if (status != null) {
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("status"), status));
                }
                if (typeId != 0) {
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("typeProject").get("id"), typeId));
                }
                if (designStyleId != 0) {
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("designStyle").get("id"), designStyleId));
                }
                return predicate;
            });
        }

        return findAll(spec, pageable);
    }


}
