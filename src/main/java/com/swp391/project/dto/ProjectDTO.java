package com.swp391.project.dto;

import com.swp391.project.entity.ProjectEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectDTO {
    private UserDetailDTO userDetailDTO;
    private int id;
    private String name;
    private String img;
    private String location;
    private String type;
    private boolean isSample;
    private String designStyleName;
    private double price;
    private Date createdAt;
    private Date updatedAt;
    private String status;

    public boolean isSample() {
        return isSample;
    }

    public void setSample(boolean sample) {
        isSample = sample;
    }
}

