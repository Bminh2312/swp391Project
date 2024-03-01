package com.swp391.project.service;

import com.swp391.project.dto.ProjectDTO;
import com.swp391.project.dto.UserDetailDTO;
import com.swp391.project.dto.UserWithProjectsDTO;
import com.swp391.project.entity.OrderProjectEntity;
import com.swp391.project.entity.ProjectEntity;
import com.swp391.project.entity.UserEntity;
import com.swp391.project.repository.OrderProjectRepository;
import com.swp391.project.repository.UserRepository;
import com.swp391.project.service.impl.UserDetailServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserDetailService implements UserDetailServiceImp {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderProjectRepository orderProjectRepository;


    @Override
    public UserDetailDTO findByEmail(String email) {
        UserEntity userEntities = userRepository.findByEmail(email);
        UserDetailDTO userDetailDTO = new UserDetailDTO();
        userDetailDTO.setAvt(userEntities.getAvt());
        userDetailDTO.setEmail(userEntities.getEmail());
        userDetailDTO.setFullName(userEntities.getFullName());
        return userDetailDTO;
    }

    @Override
    public Page<UserDetailDTO> findAll(Pageable pageable) {
        Page<UserEntity> usersPage  = userRepository.findAll(pageable);
        return usersPage.map(userEntity -> new UserDetailDTO(
                userEntity.getFullName(),
                userEntity.getEmail(),
                userEntity.getAvt(),
                userEntity.getAccessToken(),
                userEntity.getRole().getName()
        ));
    }

    public UserWithProjectsDTO getUserWithProjects(int userId) {
        UserEntity userEntity = userRepository.findById(userId).orElse(null);
        if (userEntity == null) {
            // Xử lý trường hợp không tìm thấy người dùng
            return null;
        }

        List<OrderProjectEntity> orderProjects = orderProjectRepository.findByUserId(userId);

        UserWithProjectsDTO userWithProjectsDTO = new UserWithProjectsDTO();
        userWithProjectsDTO.setUser(mapUserToDTO(userEntity));
        userWithProjectsDTO.setProjects(orderProjects.stream()
                .map(orderProject -> mapProjectToDTO(orderProject.getProject()))
                .collect(Collectors.toList()));

        return userWithProjectsDTO;
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
