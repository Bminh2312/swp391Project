package com.swp391.project.service;

import com.swp391.project.dto.UserDetailDTO;
import com.swp391.project.entity.UserEntity;
import com.swp391.project.repository.UserRepository;
import com.swp391.project.service.impl.UserDetailServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailService implements UserDetailServiceImp {
    @Autowired
    private UserRepository userRepository;


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
    public List<UserDetailDTO> findAll() {
        List<UserEntity> userEntities = userRepository.findAll();
        List<UserDetailDTO> userDetailDTOS = new ArrayList<UserDetailDTO>();
        for (UserEntity userEntity : userEntities) {
            UserDetailDTO userDetailDTO = new UserDetailDTO();
            userDetailDTO.setEmail(userEntity.getEmail());
            userDetailDTO.setFullName(userEntity.getFullName());
            userDetailDTO.setAvt(userEntity.getAvt());

            userDetailDTOS.add(userDetailDTO);
        }

        return userDetailDTOS;
    }
}
