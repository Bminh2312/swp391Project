package com.swp391.project.repository;

import com.swp391.project.dto.ProductDTO;
import com.swp391.project.entity.ProductEntity;
import com.swp391.project.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity,Integer> {
    Page<ProductEntity> findAll(Pageable pageable);

    List<ProductEntity> findByType(String type);

    @Query("SELECT DISTINCT p.type FROM product p")
    List<String> findAllType();
}
