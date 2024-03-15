package com.swp391.project.service.impl;

import com.swp391.project.dto.ProductDTO;
import com.swp391.project.dto.UserDetailDTO;
import com.swp391.project.payload.request.ProductRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductServiceImp {

    boolean create ( String name,
     String description,
     String type,
     double height,
     double length,
     double width,
     double pricePerM2, MultipartFile imgFile);

    boolean update (String name,
                    String description,
                    String type,
                    double height,
                    double length,
                    double width,
                    double pricePerM2, MultipartFile imgFile, int productId);

    Page<ProductDTO> findAll(Pageable pageable);

    Page<ProductDTO> findAllByType(String type, Pageable pageable);

    List<ProductDTO> findByType(String type);

    List<String> findAllType();

    boolean delete(int id, String status);
}
