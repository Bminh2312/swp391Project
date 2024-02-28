package com.swp391.project.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DesignStypeRequest {
    private int id;
    private String name;
    private String description;
    private Date createdAt;
    private Date updatedAt;
    private String status;
}
