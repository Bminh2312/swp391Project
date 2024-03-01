package com.swp391.project.service;

import com.swp391.project.dto.OrderProjectDTO;
import com.swp391.project.dto.ProjectDTO;
import com.swp391.project.dto.UserDetailDTO;
import com.swp391.project.entity.OrderProjectEntity;
import com.swp391.project.entity.ProjectEntity;
import com.swp391.project.entity.UserEntity;
import com.swp391.project.payload.request.OrderProjectRequest;
import com.swp391.project.repository.OrderProjectRepository;
import com.swp391.project.repository.ProjectRepository;
import com.swp391.project.repository.UserRepository;
import com.swp391.project.service.impl.OrderProjectImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderProjectService implements OrderProjectImp {

    @Autowired
    private OrderProjectRepository orderProjectRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectRepository projectRepository;



    @Override
    public boolean create(OrderProjectRequest orderProjectRequest) {
        try{
            Optional<UserEntity> userEntity = userRepository.findById(orderProjectRequest.getUserId());
            Optional<ProjectEntity> projectEntity = projectRepository.findById(orderProjectRequest.getProjectId());
            if(userEntity.isPresent() && projectEntity.isPresent()){
                TimeZone timeZone = TimeZone.getTimeZone("Asia/Ho_Chi_Minh");

                // Lấy thời gian hiện tại dựa trên múi giờ của Việt Nam
                Calendar calendar = Calendar.getInstance(timeZone);
                Date currentTime = calendar.getTime();
                OrderProjectEntity orderProjectEntity = new OrderProjectEntity();
                orderProjectEntity.setProject(projectEntity.get());
                orderProjectEntity.setUser(userEntity.get());
                orderProjectEntity.setCreatedAt(currentTime);
                orderProjectEntity.setUpdatedAt(currentTime);
                orderProjectEntity.setCost(orderProjectRequest.getCost());
                orderProjectEntity.setDescription(orderProjectRequest.getDescription());
                orderProjectRepository.save(orderProjectEntity);
                return true;
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }

        return false;
    }

    @Override
    public boolean update(int id,String status) {
        Optional<OrderProjectEntity> orderProjectEntity = orderProjectRepository.findById(id);
        if(orderProjectEntity.isPresent()){
            TimeZone timeZone = TimeZone.getTimeZone("Asia/Ho_Chi_Minh");

            // Lấy thời gian hiện tại dựa trên múi giờ của Việt Nam
            Calendar calendar = Calendar.getInstance(timeZone);
            Date currentTime = calendar.getTime();
            if(status != null ){
                orderProjectEntity.get().setStatus(status);
            }
            orderProjectEntity.get().setUpdatedAt(currentTime);
            return true;
        }
        return false;
    }

    @Override
    public List<OrderProjectDTO> getAllProjectsByUserId(int userId) {
        List<OrderProjectEntity> orderProjectEntities = orderProjectRepository.findByUserId(userId);
        return orderProjectEntities.stream()
                .map(orderProjectEntity -> {
                    OrderProjectDTO orderProjectDTO = new OrderProjectDTO();
                    orderProjectDTO.setId(orderProjectEntity.getId());
                    orderProjectDTO.setUser(mapUserToDTO(orderProjectEntity.getUser()));
                    ProjectDTO projectDTO = mapProjectToDTO(orderProjectEntity.getProject());
                    orderProjectDTO.setProject(projectDTO);
                    orderProjectDTO.setCost(orderProjectEntity.getCost());
                    orderProjectDTO.setDescription(orderProjectEntity.getDescription());
                    orderProjectDTO.setCreatedAt(orderProjectEntity.getCreatedAt());
                    orderProjectDTO.setUpdatedAt(orderProjectEntity.getUpdatedAt());
                    orderProjectDTO.setStatus(orderProjectEntity.getStatus());
                    return orderProjectDTO;
                })
                .collect(Collectors.toList());
    }

    private UserDetailDTO mapUserToDTO(UserEntity userEntity) {
        UserDetailDTO userDTO = new UserDetailDTO();
        // Thực hiện ánh xạ các trường từ entity sang DTO
        userDTO.setFullName(userEntity.getFullName());
        userDTO.setAvt(userEntity.getAvt());
        userDTO.setEmail(userEntity.getEmail());
        return userDTO;
    }

    private ProjectDTO mapProjectToDTO(ProjectEntity projectEntity) {
        ProjectDTO projectDTO = new ProjectDTO();
        // Thực hiện ánh xạ các trường từ entity sang DTO
        projectDTO.setId(projectEntity.getId());
        projectDTO.setName(projectEntity.getName());
        projectDTO.setLocation(projectEntity.getLocation());
        projectDTO.setDesignStyleName(projectEntity.getDesignStyle().getName());
        return projectDTO;
    }
}
