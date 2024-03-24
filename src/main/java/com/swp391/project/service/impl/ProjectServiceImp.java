package com.swp391.project.service.impl;

import com.swp391.project.dto.ProjectDTO;
import com.swp391.project.dto.ProjectWithAllQuoteDTO;
import com.swp391.project.dto.ProjectWithUserDTO;
import com.swp391.project.dto.UserWithProjectsDTO;
import com.swp391.project.entity.DesignStyleEntity;
import com.swp391.project.entity.ProjectEntity;
import com.swp391.project.payload.request.ProjectRequest;
import com.swp391.project.payload.request.ProjectSampleRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProjectServiceImp {
    int create(ProjectRequest projectRequest, int userId, String status);

    int createSampleProject(String name,
                            String location,
                            boolean isSample,
                            int designStyleId,
                            int typeId, int userId, String status, MultipartFile imgFile);

    boolean createBySampleProject(ProjectSampleRequest projectSampleRequest);

    boolean update(ProjectRequest projectRequest,int projectId);

    boolean updateProjectByStatus(int projectId, int userId, String status);

    long findTotalProjectByStatus(String status);

    ProjectDTO findById(int id);

    List<ProjectDTO> findByStatus(String status);

    Page<ProjectWithUserDTO> findAllByStatus(String status, Pageable pageable);

    Page<ProjectWithUserDTO> findAllByStatusOrDesignStyleIdOrTypeProject_Id(String status, int designStyleId, int typeId, Pageable pageable);

//    Page<ProjectWithUserDTO> findAllProjectSampleByStatusOrDesignStyleIdOrTypeProject_Id(String status, int designStyleId, int typeId, Pageable pageable);

    Page<ProjectDTO> findAllByStatusAndUserId(int userId, String status, Pageable pageable);

    List<ProjectDTO> findByStatusAndUserId(String status, int id);

    ProjectWithAllQuoteDTO findAllQuoteRoomByProject(int projectId);




}
