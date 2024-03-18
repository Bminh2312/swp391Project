package com.swp391.project.service;

import com.swp391.project.dto.DesignStyleDTO;
import com.swp391.project.dto.TypeProjectDTO;
import com.swp391.project.entity.DesignStyleEntity;
import com.swp391.project.entity.TypeProjectEntity;
import com.swp391.project.repository.TypeRepository;
import com.swp391.project.service.impl.TypeProjectServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TypeProjectService implements TypeProjectServiceImp {

    @Autowired
    private TypeRepository typeRepository;

    @Override
    public List<TypeProjectDTO> findAllType() {
        List<TypeProjectEntity> typeProjectEntities = typeRepository.findAll();
        List<TypeProjectDTO> typeProjectDTOS = new ArrayList<>();
        for (TypeProjectEntity typeProjectEntity : typeProjectEntities) {
            TypeProjectDTO typeProjectDTO = new TypeProjectDTO(
                    typeProjectEntity.getId(),
                    typeProjectEntity.getName(),
                    typeProjectEntity.getPriceType(),
                    typeProjectEntity.getCreatedAt(),
                    typeProjectEntity.getUpdatedAt(),
                    typeProjectEntity.getStatus()
            );
            typeProjectDTOS.add(typeProjectDTO);
        }
        return typeProjectDTOS;
    }

    @Override
    public TypeProjectDTO findById(int id) {
        Optional<TypeProjectEntity> typeProjectEntity= typeRepository.findById(id);
        TypeProjectDTO typeProjectDTO = new TypeProjectDTO();
        if(typeProjectEntity.isPresent()){
            typeProjectDTO.setId(typeProjectEntity.get().getId());
            typeProjectDTO.setName(typeProjectEntity.get().getName());
            typeProjectDTO.setPrice(typeProjectEntity.get().getPriceType());
            typeProjectDTO.setCreatedAt(typeProjectEntity.get().getCreatedAt());
            typeProjectDTO.setUpdatedAt(typeProjectEntity.get().getUpdatedAt());
            typeProjectDTO.setStatus(typeProjectEntity.get().getStatus());
            return typeProjectDTO;
        }
        return null;
    }
}
