package com.swp391.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private String name;
    private String img;
    private String description;
    private String type;
    private double height;
    private double length;
    private double width;
    private double pricePerM2;
    private Date createdAt;
    private Date updatedAt;
    private String status;
}
