package com.swp391.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuoteDTO {
    private int id;
    private List<QuoteDetailDTO> quoteDetailDTOs;
    private ProjectDTO projectDTO;
    private RoomDTO roomDTO;
    private Date quoteDate;
    private double area;
    private Date createdAt;
    private Date updatedAt;
    private String status;

}
