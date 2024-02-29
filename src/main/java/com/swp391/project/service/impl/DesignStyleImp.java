package com.swp391.project.service.impl;

import com.swp391.project.dto.DesignStyleDTO;
import com.swp391.project.payload.request.DesignStypeRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface DesignStyleImp {

    Page<DesignStyleDTO> findAllDesign(Pageable pageable);

    boolean create(DesignStypeRequest designTypeRequest, MultipartFile file);

    boolean update(DesignStypeRequest designTypeRequest, MultipartFile file, String status, int designId);

    DesignStyleDTO findById(int id);
}
