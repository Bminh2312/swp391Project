package com.swp391.project.service.impl;

import com.swp391.project.dto.ProjectDTO;
import com.swp391.project.payload.request.ProjectRequest;

public interface ProjectServiceImp {
    boolean create(ProjectRequest projectRequest);

    boolean update(ProjectRequest projectRequest,int projectId);

    ProjectDTO findById(int id);


}