package com.swp391.project.service.impl;

import com.swp391.project.dto.ProjectDTO;
import com.swp391.project.payload.request.DesignStypeRequest;
import com.swp391.project.payload.request.ProjectRequest;
import org.springframework.web.multipart.MultipartFile;

public interface ProjectImp {
    boolean create(ProjectRequest projectRequest);

    boolean update(ProjectRequest projectRequest,int projectId);

    ProjectDTO findById(int id);


}
