package com.swp391.project.service.impl;

import com.swp391.project.dto.UserDetailDTO;
import com.swp391.project.dto.UserWithProjectsDTO;
import com.swp391.project.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserDetailServiceImp {
    UserDetailDTO findByEmail(String email);

    Page<UserDetailDTO> findAll(Pageable pageable);

    UserWithProjectsDTO getUserWithProjects(int userId);

}
