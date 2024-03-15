package com.swp391.project.repository;

import com.swp391.project.entity.ProjectEntity;
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

    Page<UserEntity> findAll(Pageable pageable);

    Page<UserEntity> findAllByStatus(int userId,String status, Pageable pageable);


}
