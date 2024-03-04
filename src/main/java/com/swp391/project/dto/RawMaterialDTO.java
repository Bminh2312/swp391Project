package com.swp391.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RawMaterialDTO {
    private int id;
    private String name;
    private String img;
    private String description;
    private String type;
    private double area;
    private double pricePerM2;
    private List<QuoteDetailDTO> quoteDetailDTOList;
    private Date createdAt;
    private Date updatedAt;
    private String status;
}
