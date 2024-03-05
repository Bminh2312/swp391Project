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
    private String note;
    private int quoteId;
    private int productId;
    private int rawMaterialId;
    private Date createdAt;
    private Date updatedAt;
    private String status;
}
