package com.swp391.project.payload.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderProjectRequest {
    private int userId;
    private int projectId;
    private double cost;
    private String description;
    private String status;
}
