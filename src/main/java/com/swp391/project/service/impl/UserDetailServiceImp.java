package com.swp391.project.service.impl;

import com.swp391.project.dto.UserDetailDTO;
import com.swp391.project.entity.UserEntity;

import java.util.List;

public interface UserDetailServiceImp {
    UserDetailDTO findByEmail(String email);

    List<UserDetailDTO> findAll();

}
