package com.swp391.project.service.impl;

import com.swp391.project.dto.ProjectDTO;
import com.swp391.project.dto.ProjectWithAllQuoteDTO;
import com.swp391.project.dto.UserWithProjectsDTO;
import com.swp391.project.payload.request.ProjectRequest;

import java.util.List;

public interface ProjectServiceImp {
    int create(ProjectRequest projectRequest, int userId, String status);

    boolean update(ProjectRequest projectRequest,int projectId);

    boolean updateProjectByStatus(int projectId, String status);

    ProjectDTO findById(int id);

    List<ProjectDTO> findByStatus(String status);

    List<ProjectDTO> findByStatusAndUserId(String status, int id);

    ProjectWithAllQuoteDTO findAllQuoteRoomByProject(int projectId);


}
