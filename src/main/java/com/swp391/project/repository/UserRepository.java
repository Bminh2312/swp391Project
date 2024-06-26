package com.swp391.project.repository;

import com.swp391.project.entity.ProjectEntity;
import com.swp391.project.entity.RoleEntity;
import com.swp391.project.entity.UserEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Integer> {

    UserEntity findByEmail(String email);

    UserEntity findByUsername(String username);

    Page<UserEntity> findAllByRole_Id(int roleId, Pageable pageable);

    Page<UserEntity> findAllByStatus(int userId,String status, Pageable pageable);

    long countByStatus(String status);

    long countByStatusAndRole_Id(String status, int roleId);

}
