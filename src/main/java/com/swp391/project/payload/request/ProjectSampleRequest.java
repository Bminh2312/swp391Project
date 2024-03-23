package com.swp391.project.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectSampleRequest {
    private int projectSampleId;
    private int userId;
    private String name;
    private String location;

}
