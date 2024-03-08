package com.swp391.project.service;

import com.swp391.project.dto.ProjectDTO;
import com.swp391.project.dto.UserDetailDTO;
import com.swp391.project.entity.DesignStyleEntity;
import com.swp391.project.entity.ProjectEntity;
import com.swp391.project.entity.UserEntity;
import com.swp391.project.payload.request.ProjectRequest;
import com.swp391.project.repository.DesignStyleRepository;
import com.swp391.project.repository.ProjectRepository;

import com.swp391.project.repository.UserRepository;
import com.swp391.project.service.impl.ProjectServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.TimeZone;

@Service
public class ProjectService implements ProjectServiceImp {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private DesignStyleRepository designStyleRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean create(ProjectRequest projectRequest , int userId) {
        try{
            Optional<DesignStyleEntity> designStyleEntity = designStyleRepository.findById(projectRequest.getDesignStyleId());
            if(designStyleEntity.isPresent()){
                TimeZone timeZone = TimeZone.getTimeZone("Asia/Ho_Chi_Minh");

                // Lấy thời gian hiện tại dựa trên múi giờ của Việt Nam
                Calendar calendar = Calendar.getInstance(timeZone);
                Date currentTime = calendar.getTime();
                ProjectEntity projectEntity = new ProjectEntity();
                Optional<UserEntity> userEntity = userRepository.findById(userId);
                userEntity.ifPresent(projectEntity::setUser);
                projectEntity.setName(projectRequest.getName());
                projectEntity.setLocation(projectRequest.getLocation());
                projectEntity.setType(projectRequest.getType());
                projectEntity.setDesignStyle(designStyleEntity.get());
                projectEntity.setCreatedAt(currentTime);
                projectEntity.setUpdatedAt(currentTime);
                projectRepository.save(projectEntity);
                return true;
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }

        return false;
    }



    @Override
    public boolean update(ProjectRequest projectRequest,int projectId) {
        try{
            Optional<ProjectEntity> projectEntity = projectRepository.findById(projectId);
            Optional<DesignStyleEntity> designStyleEntity = designStyleRepository.findById(projectRequest.getDesignStyleId());
            if(projectEntity.isPresent()){
                TimeZone timeZone = TimeZone.getTimeZone("Asia/Ho_Chi_Minh");

                // Lấy thời gian hiện tại dựa trên múi giờ của Việt Nam
                Calendar calendar = Calendar.getInstance(timeZone);
                Date currentTime = calendar.getTime();
                if(projectRequest.getName() != null){
                    projectEntity.get().setName(projectRequest.getName());
                }
                 if(projectRequest.getLocation() != null){
                     projectEntity.get().setLocation(projectRequest.getLocation());
                 }

                 if(projectRequest.getType() != null ){
                     projectEntity.get().setType(projectRequest.getType());
                 }
                designStyleEntity.ifPresent(styleEntity -> projectEntity.get().setDesignStyle(styleEntity));
                projectEntity.get().setUpdatedAt(currentTime);
                projectRepository.save(projectEntity.get());
                return true;
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
        return false;
    }



    @Override
    public ProjectDTO findById(int id) {
        Optional<ProjectEntity> projectEntity = projectRepository.findById(id);
        ProjectDTO projectDTO = new ProjectDTO();
        if(projectEntity.isPresent()){
            projectDTO.setId(projectEntity.get().getId());
            projectDTO.setName(projectEntity.get().getName());
            projectDTO.setDesignStyleName(projectEntity.get().getDesignStyle().getName());
            projectDTO.setCreatedAt(projectEntity.get().getCreatedAt());
            projectDTO.setUpdatedAt(projectEntity.get().getUpdatedAt());
            projectDTO.setStatus(projectEntity.get().getStatus());
            return projectDTO;
        }
        return null;
    }

    @Override
    public ProjectDTO findByStatus(String status) {
        Optional<ProjectEntity> projectEntity = projectRepository.findByStatus(status);
        ProjectDTO projectDTO = new ProjectDTO();
        if(projectEntity.isPresent()){
            projectDTO.setId(projectEntity.get().getId());
            projectDTO.setUserDetailDTO(mapUserEntityToDTO(projectEntity.get().getUser()));
            projectDTO.setName(projectEntity.get().getName());
            projectDTO.setDesignStyleName(projectEntity.get().getDesignStyle().getName());
            projectDTO.setCreatedAt(projectEntity.get().getCreatedAt());
            projectDTO.setUpdatedAt(projectEntity.get().getUpdatedAt());
            projectDTO.setStatus(projectEntity.get().getStatus());
            return projectDTO;
        }
        return null;
    }

    public UserDetailDTO mapUserEntityToDTO(UserEntity userEntity) {
        UserDetailDTO userDetailDTO = new UserDetailDTO();
        userDetailDTO.setId(userEntity.getId());
        userDetailDTO.setFullName(userEntity.getFullName());
        userDetailDTO.setEmail(userEntity.getEmail());
        userDetailDTO.setAvt(userEntity.getAvt());
        userDetailDTO.setAccessToken(userEntity.getAccessToken());
        userDetailDTO.setRole(userEntity.getRole().getName()); // Assumed role has a name attribute

        return userDetailDTO;
    }

}
