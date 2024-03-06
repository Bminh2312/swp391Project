package com.swp391.project.dto;

import lombok.Data;

import java.util.Date;

@Data
public class RoomDTO {
    private int id;
    private String name;
    private ProjectDTO projectDTO;
    private Date createdAt;
    private Date updatedAt;
    private String status;
}
