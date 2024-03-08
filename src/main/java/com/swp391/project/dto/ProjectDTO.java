package com.swp391.project.dto;

import com.swp391.project.entity.ProjectEntity;
import lombok.Data;

import java.util.Date;

@Data
public class ProjectDTO {
    private UserDetailDTO userDetailDTO;
    private int id;
    private String name;
    private String location;
    private String type;
    private boolean isSample;
    private String designStyleName;
    private Date createdAt;
    private Date updatedAt;
    private String status;
}

