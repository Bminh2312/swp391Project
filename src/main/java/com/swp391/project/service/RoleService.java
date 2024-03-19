package com.swp391.project.service;

import com.swp391.project.dto.RoleDTO;
import com.swp391.project.dto.TypeProjectDTO;
import com.swp391.project.entity.RoleEntity;
import com.swp391.project.entity.TypeProjectEntity;
import com.swp391.project.repository.RoleRepository;
import com.swp391.project.service.impl.RoleServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RoleService implements RoleServiceImp {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public List<RoleDTO> findAllType() {
        List<RoleEntity> roleEntities = roleRepository.findAll();
        List<RoleDTO> roleDTOS = new ArrayList<>();
        for (RoleEntity roleEntity : roleEntities) {
            RoleDTO roleDTO = new RoleDTO(
                    roleEntity.getId(),
                    roleEntity.getName(),
                    roleEntity.getCreatedAt(),
                    roleEntity.getUpdatedAt(),
                    roleEntity.getStatus()
            );
            roleDTOS.add(roleDTO);
        }
        return roleDTOS;
    }

    @Override
    public RoleDTO findById(int id) {
        Optional<RoleEntity> roleEntity= roleRepository.findById(id);
        RoleDTO roleDTO = new RoleDTO();
        if(roleEntity.isPresent()){
            roleDTO.setId(roleEntity.get().getId());
            roleDTO.setName(roleEntity.get().getName());
            roleDTO.setCreatedAt(roleEntity.get().getCreatedAt());
            roleDTO.setUpdatedAt(roleEntity.get().getUpdatedAt());
            roleDTO.setStatus(roleEntity.get().getStatus());
            return roleDTO;
        }
        return null;
    }
}
