package com.swp391.project.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectWithUserDTO {
    private UserDetailDTO userDetailDTO;
    private int id;
    private String name;
    private String img;
    private String location;
    private String type;
    private boolean isSample;
    private String designStyleName;
    private Date createdAt;
    private Date updatedAt;
    private String status;
}
