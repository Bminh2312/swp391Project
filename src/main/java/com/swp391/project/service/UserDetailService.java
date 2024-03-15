package com.swp391.project.service;

import com.google.api.gax.rpc.NotFoundException;
import com.swp391.project.dto.ProjectDTO;
import com.swp391.project.dto.UserDetailDTO;
import com.swp391.project.dto.UserWithProjectsDTO;
import com.swp391.project.entity.ProjectEntity;
import com.swp391.project.entity.RoomEntity;
import com.swp391.project.entity.UserEntity;
//import com.swp391.project.repository.OrderProjectRepository;
import com.swp391.project.repository.UserRepository;
import com.swp391.project.service.impl.ProjectServiceImp;
import com.swp391.project.service.impl.UserDetailServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserDetailService implements UserDetailServiceImp {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectServiceImp projectServiceImp;

    @Autowired
    private FireBaseStorageService fireBaseStorageService;

    @Override
    public UserDetailDTO findByEmail(String email) {
        UserEntity userEntities = userRepository.findByEmail(email);
        UserDetailDTO userDetailDTO = new UserDetailDTO();
        userDetailDTO.setId(userEntities.getId());
        userDetailDTO.setAvt(userEntities.getAvt());
        userDetailDTO.setPhone(userEntities.getPhone());
        userDetailDTO.setAddress(userEntities.getAddress());
        userDetailDTO.setEmail(userEntities.getEmail());
        userDetailDTO.setFullName(userEntities.getFullName());
        userDetailDTO.setRole(userEntities.getRole().getName());
        userDetailDTO.setStatus(userEntities.getStatus());
        return userDetailDTO;
    }

    @Override
    public UserDetailDTO findById(int id) {
        try{
            Optional<UserEntity> userEntities = userRepository.findById(id);
            if(userEntities.isPresent()){
                UserDetailDTO userDetailDTO = new UserDetailDTO();
                userDetailDTO.setId(userEntities.get().getId());
                userDetailDTO.setAvt(userEntities.get().getAvt());
                userDetailDTO.setPhone(userEntities.get().getPhone());
                userDetailDTO.setAddress(userEntities.get().getAddress());
                userDetailDTO.setEmail(userEntities.get().getEmail());
                userDetailDTO.setFullName(userEntities.get().getFullName());
                userDetailDTO.setRole(userEntities.get().getRole().getName());
                userDetailDTO.setStatus(userEntities.get().getStatus());
                return userDetailDTO;
            }

            return null;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Page<UserDetailDTO> findAll(Pageable pageable) {
        Page<UserEntity> usersPage = userRepository.findAll(pageable);
        return usersPage.map(userEntity -> new UserDetailDTO(
                userEntity.getId(),
                userEntity.getFullName(),
                userEntity.getEmail(),
                userEntity.getAvt(),
                userEntity.getPhone(),
                userEntity.getAddress(),
                userEntity.getAccessToken(),
                userEntity.getRole().getName(),
                userEntity.getStatus()
        ));
    }

    @Override
    public UserWithProjectsDTO getUserWithProjects(String status, int userId) {
        UserEntity userEntity = userRepository.findById(userId).orElse(null);
        if (userEntity == null) {
            // Xử lý trường hợp không tìm thấy người dùng
            return null;
        }

        List<ProjectDTO> projectDTOS = projectServiceImp.findByStatusAndUserId(status,userId);

        UserWithProjectsDTO userWithProjectsDTO = new UserWithProjectsDTO();
        userWithProjectsDTO.setUser(mapUserToDTO(userEntity));
        userWithProjectsDTO.setProjects(projectDTOS);

        return userWithProjectsDTO;
    }


    private UserDetailDTO mapUserToDTO(UserEntity userEntity) {
        UserDetailDTO userDTO = new UserDetailDTO();
        // Thực hiện ánh xạ các trường từ entity sang DTO
        userDTO.setId(userEntity.getId());
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

    @Override
    public Boolean updateUser(int userId, String fullName, String phone, String address, MultipartFile avt) {
        try {
            Optional<UserEntity> user = userRepository.findById(userId);
            if(user.isEmpty()) {
                return false;
            }else{
                if (fullName != null && !fullName.trim().isEmpty()) {
                    user.get().setFullName(fullName);
                }
                if (phone != null && phone.matches("[0-9]+") && phone.length() == 10){
                    user.get().setPhone(phone);
                }
                if (address != null && !address.trim().isEmpty()) {
                    user.get().setAddress(address);
                }
                if (avt != null) {
                    user.get().setAvt(fireBaseStorageService.uploadImage(avt));
                }
                userRepository.save(user.get());
                return true;
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(int id, String status) {
        try {
            TimeZone timeZone = TimeZone.getTimeZone("Asia/Ho_Chi_Minh");
            Optional<UserEntity> userEntity = userRepository.findById(id);
            if (userEntity.isPresent()) {
                // Lấy thời gian hiện tại dựa trên múi giờ của Việt Nam
                Calendar calendar = Calendar.getInstance(timeZone);
                Date currentTime = calendar.getTime();
                userEntity.get().setStatus(status);
                userEntity.get().setUpdatedAt(currentTime);
                userRepository.save(userEntity.get());
                return true;
            }else{
                return false;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

}
