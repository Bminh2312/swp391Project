package com.swp391.project.service.impl;

import com.swp391.project.dto.DesignStyleDTO;
import com.swp391.project.payload.request.DesignStypeRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface DesignStyleServiceImp {

    List<DesignStyleDTO> findAllDesign();

    boolean create(String name, String description, MultipartFile file);

    boolean update(String name, String description, MultipartFile file, String status, int designId);

    DesignStyleDTO findById(int id);
}
