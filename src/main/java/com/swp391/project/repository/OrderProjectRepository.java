package com.swp391.project.repository;

import com.swp391.project.entity.OrderProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderProjectRepository extends JpaRepository<OrderProjectEntity,Integer> {
    List<OrderProjectEntity> findByUserId(int userId);
}
