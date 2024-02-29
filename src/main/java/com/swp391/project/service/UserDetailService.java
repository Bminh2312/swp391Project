package com.swp391.project.service;

import com.swp391.project.dto.UserDetailDTO;
import com.swp391.project.entity.UserEntity;
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
}
