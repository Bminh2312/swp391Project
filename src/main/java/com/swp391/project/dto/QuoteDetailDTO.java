package com.swp391.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuoteDetailDTO {
    private int id;
    private int quantity;
    private double price;
    private double area;
    private String note;
    private int quoteId;
    private ProductDTO product;
    private RawMaterialDTO rawMaterial;
    private Date createdAt;
    private Date updatedAt;
    private String status;
}
