package com.swp391.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderProjectDTO {
    private int id;
    private UserDetailDTO user;
    private ProjectDTO project;
    private double cost;
    private String description;
    private Date createdAt;
    private Date updatedAt;
    private String status;

}
