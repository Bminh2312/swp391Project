package com.swp391.project.service.impl;

import com.swp391.project.dto.ProductDTO;
import com.swp391.project.dto.RawMaterialDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface RawMaterialServiceImp {
    boolean create ( String name,
                     String description,
                     String type,
                     double area,
                     double pricePerM2, MultipartFile imgFile);

    boolean update (String name,
                    String description,
                    String type,
                    double area,
                    double pricePerM2, MultipartFile imgFile, int rawMaterialId);

    Page<RawMaterialDTO> findAll(Pageable pageable);
}
