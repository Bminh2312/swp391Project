package com.swp391.project.service.impl;

import com.swp391.project.dto.DesignStyleDTO;
import com.swp391.project.dto.TypeProjectDTO;

import java.util.List;

public interface TypeProjectServiceImp {
    List<TypeProjectDTO> findAllType();

    TypeProjectDTO findById(int id);


}
