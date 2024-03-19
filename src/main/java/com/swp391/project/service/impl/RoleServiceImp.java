package com.swp391.project.service.impl;

import com.swp391.project.dto.RoleDTO;
import com.swp391.project.dto.TypeProjectDTO;

import java.util.List;

public interface RoleServiceImp {
    List<RoleDTO> findAllType();

    RoleDTO findById(int id);
}
