package com.swp391.project.service;

import com.swp391.project.dto.ProjectDTO;
import com.swp391.project.dto.UserDetailDTO;
import com.swp391.project.dto.UserWithProjectsDTO;
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

import java.util.*;

@Service
public class ProjectService implements ProjectServiceImp {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private DesignStyleRepository designStyleRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public int create(ProjectRequest projectRequest , int userId, String status) {
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
                projectEntity.setSample(false);
                projectEntity.setStatus(status);
                projectEntity.setCreatedAt(currentTime);
                projectEntity.setUpdatedAt(currentTime);
                ProjectEntity projectEntityRespone =  projectRepository.save(projectEntity);
                return projectEntityRespone.getId();
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            return 0;
        }

        return 0;
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
    public boolean updateProjectByStatus(int projectId, String status) {
        try{
            Optional<ProjectEntity> projectEntity = projectRepository.findById(projectId);
            if(projectEntity.isPresent()){
                TimeZone timeZone = TimeZone.getTimeZone("Asia/Ho_Chi_Minh");

                // Lấy thời gian hiện tại dựa trên múi giờ của Việt Nam
                Calendar calendar = Calendar.getInstance(timeZone);
                Date currentTime = calendar.getTime();
                projectEntity.get().setStatus(status);
                projectEntity.get().setUpdatedAt(currentTime);
                projectRepository.save(projectEntity.get());
                return true;
            }
            return false;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }

    }


    @Override
    public ProjectDTO findById(int id) {
        Optional<ProjectEntity> projectEntity = projectRepository.findById(id);
        ProjectDTO projectDTO = new ProjectDTO();
        if(projectEntity.isPresent()){
            projectDTO.setId(projectEntity.get().getId());
            projectDTO.setName(projectEntity.get().getName());
            projectDTO.setDesignStyleName(projectEntity.get().getDesignStyle().getName());
            projectDTO.setLocation(projectEntity.get().getLocation());
            projectDTO.setType(projectEntity.get().getType());
            projectDTO.setSample(projectEntity.get().isSample());
            projectDTO.setCreatedAt(projectEntity.get().getCreatedAt());
            projectDTO.setUpdatedAt(projectEntity.get().getUpdatedAt());
            projectDTO.setStatus(projectEntity.get().getStatus());
            return projectDTO;
        }
        return null;
    }

    @Override
    public List<ProjectDTO> findByStatus(String status) {
        List<ProjectEntity> projectEntities = projectRepository.findByStatus(status);
        List<ProjectDTO> projectDTOS = new ArrayList<>();
        if(!projectEntities.isEmpty()){
            for (ProjectEntity projectEntity: projectEntities) {
                ProjectDTO projectDTO = new ProjectDTO();
                projectDTO.setId(projectEntity.getId());
                if(projectEntity.getUser() != null){
                    projectDTO.setUserDetailDTO(mapUserEntityToDTO(projectEntity.getUser()));
                }
                projectDTO.setName(projectEntity.getName());
                projectDTO.setDesignStyleName(projectEntity.getDesignStyle().getName());
                projectDTO.setLocation(projectEntity.getLocation());
                projectDTO.setType(projectEntity.getType());
                projectDTO.setCreatedAt(projectEntity.getCreatedAt());
                projectDTO.setUpdatedAt(projectEntity.getUpdatedAt());
                projectDTO.setStatus(projectEntity.getStatus());
                projectDTO.setSample(projectEntity.isSample());
                projectDTOS.add(projectDTO);
            }
            return projectDTOS;
        }
        return null;
    }

    @Override
    public  List<ProjectDTO> findByStatusAndUserId(String status,int id) {
        List<ProjectEntity> projectEntities = projectRepository.findByStatusAndUserId(status,id);
        List<ProjectDTO> projectDTOS = new ArrayList<>();
        if(!projectEntities.isEmpty()){
            for (ProjectEntity projectEntity: projectEntities) {
                ProjectDTO projectDTO = new ProjectDTO();
                projectDTO.setId(projectEntity.getId());
                projectDTO.setName(projectEntity.getName());
                projectDTO.setLocation(projectEntity.getLocation());
                projectDTO.setType(projectEntity.getType());
                projectDTO.setDesignStyleName(projectEntity.getDesignStyle().getName());
                projectDTO.setCreatedAt(projectEntity.getCreatedAt());
                projectDTO.setUpdatedAt(projectEntity.getUpdatedAt());
                projectDTO.setStatus(projectEntity.getStatus());
                projectDTO.setSample(projectEntity.isSample());
                projectDTOS.add(projectDTO);
            }
            return projectDTOS;
        }
        return null;
    }

    public ProjectDTO mapProjectAndUserToDTO(ProjectEntity projectEntity) {
        if (projectEntity == null) {
            return null;
        }

        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setId(projectEntity.getId());
        projectDTO.setName(projectEntity.getName());
        projectDTO.setLocation(projectEntity.getLocation());
        projectDTO.setType(projectEntity.getType());
        projectDTO.setSample(projectEntity.isSample());
        projectDTO.setDesignStyleName(projectEntity.getDesignStyle().getName());
        projectDTO.setCreatedAt(projectEntity.getCreatedAt());
        projectDTO.setUpdatedAt(projectEntity.getUpdatedAt());
        projectDTO.setStatus(projectEntity.getStatus());

        // Tạo đối tượng UserDetailDTO và thiết lập thông tin người dùng
        UserDetailDTO userDetailDTO = new UserDetailDTO();
        userDetailDTO.setId(projectEntity.getUser().getId());
        userDetailDTO.setFullName(projectEntity.getUser().getFullName());
        userDetailDTO.setEmail(projectEntity.getUser().getEmail());
        userDetailDTO.setAvt(projectEntity.getUser().getAvt());
        userDetailDTO.setAccessToken(projectEntity.getUser().getAccessToken());
        userDetailDTO.setRole(projectEntity.getUser().getRole().getName());

        // Thiết lập thông tin người dùng vào ProjectDTO
        projectDTO.setUserDetailDTO(userDetailDTO);

        return projectDTO;
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
