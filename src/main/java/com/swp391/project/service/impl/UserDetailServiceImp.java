package com.swp391.project.service.impl;

import com.swp391.project.dto.UserDetailDTO;
import com.swp391.project.dto.UserWithProjectsDTO;
import com.swp391.project.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserDetailServiceImp {
    UserDetailDTO findByEmail(String email);

    UserDetailDTO findById(int id);

    Page<UserDetailDTO> findAll(Pageable pageable, int roleId);

    UserWithProjectsDTO getUserWithProjects(String status, int userId);


    Boolean updateUser(int userId, String fullName, String phone, String address, MultipartFile avt);

    boolean delete(int id, String status);

    long countByStatus(String status);

    long countByStatusAndRole_Id(String status, int roleId);


}
