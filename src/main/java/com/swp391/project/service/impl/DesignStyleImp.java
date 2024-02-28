package com.swp391.project.service.impl;

import com.swp391.project.dto.DesignStyleDTO;
import com.swp391.project.payload.request.DesignStypeRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface DesignStyleImp {
    List<DesignStyleDTO> findAllDesign();

    boolean create(DesignStypeRequest designTypeRequest, MultipartFile file);

    boolean update(DesignStypeRequest designTypeRequest, MultipartFile file);

    DesignStyleDTO findById(int id);
}
