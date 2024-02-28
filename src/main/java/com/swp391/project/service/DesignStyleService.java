package com.swp391.project.service;

import com.swp391.project.dto.DesignStyleDTO;
import com.swp391.project.entity.DesignStyleEntity;
import com.swp391.project.payload.request.DesignStypeRequest;
import com.swp391.project.repository.DesignStyleRepository;
import com.swp391.project.service.impl.DesignStyleImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DesignStyleService implements DesignStyleImp {

    @Autowired
    private DesignStyleRepository designStyleRepository;

    @Autowired
    private FireBaseStorageService fireBaseStorageService;

    @Override
    public List<DesignStyleDTO> findAllDesign() {
        List<DesignStyleDTO> designStyleDTOList = new ArrayList<DesignStyleDTO>();
        List<DesignStyleEntity> designStyleEntity = designStyleRepository.findAll();
        for (DesignStyleEntity entity : designStyleEntity) {
            DesignStyleDTO dto = new DesignStyleDTO();
            dto.setId(entity.getId());
            dto.setName(entity.getName());
            dto.setImg(entity.getImg());
            dto.setDescription(entity.getDescription());
            dto.setUpdatedAt(entity.getUpdatedAt());
            dto.setCreatedAt(entity.getCreatedAt());
            dto.setStatus(entity.getStatus());// Assuming setName method is used to set the name
            // You may need to set other properties of dto here as well
            designStyleDTOList.add(dto);
        }
        return designStyleDTOList;
    }

    @Override
    public boolean create(DesignStypeRequest designTypeRequest, MultipartFile file) {
        try{
            DesignStyleEntity designStyle = new DesignStyleEntity();
            designStyle.setId(designTypeRequest.getId());
            designStyle.setName(designTypeRequest.getName());
            if(file != null){
                String img = fireBaseStorageService.uploadImage(file);
                designStyle.setImg(img);
            }

            designStyle.setCreatedAt(designTypeRequest.getCreatedAt());
            designStyle.setUpdatedAt(designTypeRequest.getUpdatedAt());
            designStyle.setStatus(designTypeRequest.getStatus());
            designStyleRepository.save(designStyle);
            return true;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }


    }

    @Override
    public boolean update(DesignStypeRequest designTypeRequest, MultipartFile file) {
        try{
            Optional<DesignStyleEntity> designStyleEntity = designStyleRepository.findById(designTypeRequest.getId());
            if(designStyleEntity.isEmpty()){
                return false;
            }else{

                designStyleEntity.get().setId(designTypeRequest.getId());
                designStyleEntity.get().setName(designTypeRequest.getName());
                if(file != null){
                    String img = fireBaseStorageService.uploadImage(file);
                    designStyleEntity.get().setImg(img);
                }
                designStyleEntity.get().setCreatedAt(designTypeRequest.getCreatedAt());
                designStyleEntity.get().setUpdatedAt(designTypeRequest.getUpdatedAt());
                designStyleEntity.get().setStatus(designTypeRequest.getStatus());
                designStyleRepository.save(designStyleEntity.get());
                return true;
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public DesignStyleDTO findById(int id) {
        Optional<DesignStyleEntity> designStyleEntity = designStyleRepository.findById(id);
        DesignStyleDTO designStyleDTO = new DesignStyleDTO();
        if(designStyleEntity.isPresent()){
            designStyleDTO.setId(designStyleEntity.get().getId());
            designStyleDTO.setName(designStyleEntity.get().getName());
            designStyleDTO.setImg(designStyleEntity.get().getImg());
            designStyleDTO.setDescription(designStyleEntity.get().getDescription());
            designStyleDTO.setCreatedAt(designStyleEntity.get().getCreatedAt());
            designStyleDTO.setUpdatedAt(designStyleEntity.get().getUpdatedAt());
            designStyleDTO.setStatus(designStyleEntity.get().getStatus());
            return designStyleDTO;
        }
        return null;
    }


}
