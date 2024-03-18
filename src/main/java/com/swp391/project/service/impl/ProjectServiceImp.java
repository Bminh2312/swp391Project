package com.swp391.project.service.impl;

import com.swp391.project.dto.ProjectDTO;
import com.swp391.project.dto.ProjectWithAllQuoteDTO;
import com.swp391.project.dto.ProjectWithUserDTO;
import com.swp391.project.dto.UserWithProjectsDTO;
import com.swp391.project.entity.DesignStyleEntity;
import com.swp391.project.entity.ProjectEntity;
import com.swp391.project.payload.request.ProjectRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProjectServiceImp {
    int create(ProjectRequest projectRequest, int userId, String status);

    boolean update(ProjectRequest projectRequest,int projectId);

    boolean updateProjectByStatus(int projectId, String status);

    ProjectDTO findById(int id);

    List<ProjectDTO> findByStatus(String status);

    Page<ProjectWithUserDTO> findAllByStatus(String status, Pageable pageable);

    Page<ProjectWithUserDTO> findAllByStatusOrDesignStyleOrType(String status, int designStyleId, int typeId, Pageable pageable);

    Page<ProjectDTO> findAllByStatusAndUserId(int userId, String status, Pageable pageable);

    List<ProjectDTO> findByStatusAndUserId(String status, int id);

    ProjectWithAllQuoteDTO findAllQuoteRoomByProject(int projectId);




}
