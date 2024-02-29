package com.swp391.project.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ProjectDTO {
    private int id;
    private String name;
    private String location;
    private boolean isSample;
    private String designStyleName;
    private Date createdAt;
    private Date updatedAt;
    private String status;
}
