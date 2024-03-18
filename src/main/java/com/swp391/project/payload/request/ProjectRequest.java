package com.swp391.project.payload.request;

import com.swp391.project.dto.DesignStyleDTO;
import com.swp391.project.entity.DesignStyleEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectRequest {
    private String name;
    private String location;
    private boolean isSample;
    private int designStyleId;
    private int typeId;
}
