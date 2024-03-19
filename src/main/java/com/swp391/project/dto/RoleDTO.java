package com.swp391.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleDTO {
    private int id;
    private String name;
    private Date createdAt;
    private Date updatedAt;
    private String status;
}
