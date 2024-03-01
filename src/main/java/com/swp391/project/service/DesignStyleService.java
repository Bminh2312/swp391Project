package com.swp391.project.service;

import com.swp391.project.dto.DesignStyleDTO;
import com.swp391.project.entity.DesignStyleEntity;
import com.swp391.project.payload.request.DesignStypeRequest;
import com.swp391.project.repository.DesignStyleRepository;
import com.swp391.project.service.impl.DesignStyleServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
public class DesignStyleService implements DesignStyleServiceImp {

    @Autowired
    private DesignStyleRepository designStyleRepository;

    @Autowired
    private FireBaseStorageService fireBaseStorageService;


    @Override
    public List<DesignStyleDTO> findAllDesign() {
        List<DesignStyleEntity> designStyleEntities = designStyleRepository.findAll();
        List<DesignStyleDTO> designStyleDTOs = new ArrayList<>();
        for (DesignStyleEntity designEntity : designStyleEntities) {
            DesignStyleDTO designStyleDTO = new DesignStyleDTO(
                    designEntity.getId(),
                    designEntity.getName(),
                    designEntity.getImg(),
                    designEntity.getDescription(),
                    designEntity.getCreatedAt(),
                    designEntity.getUpdatedAt(),
                    designEntity.getStatus()
            );
            designStyleDTOs.add(designStyleDTO);
        }
        return designStyleDTOs;
    }


    @Override
    public boolean create(String name, String description, MultipartFile file) {
        try{

            TimeZone timeZone = TimeZone.getTimeZone("Asia/Ho_Chi_Minh");

            // Lấy thời gian hiện tại dựa trên múi giờ của Việt Nam
            Calendar calendar = Calendar.getInstance(timeZone);
            Date currentTime = calendar.getTime();
            DesignStyleEntity designStyle = new DesignStyleEntity();
            designStyle.setName(name);
            designStyle.setDescription(description);
            designStyle.setStatus("ACTIVE");

            if(file != null){
                String img = fireBaseStorageService.uploadImage(file);
                designStyle.setImg(img);
            }

            designStyle.setCreatedAt(currentTime);
            designStyle.setUpdatedAt(currentTime);
            designStyleRepository.save(designStyle);
            return true;

        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }


    }

    @Override
    public boolean update(String name, String description, MultipartFile file, String status, int designId) {
        try{
            TimeZone timeZone = TimeZone.getTimeZone("Asia/Ho_Chi_Minh");

            // Lấy thời gian hiện tại dựa trên múi giờ của Việt Nam
            Calendar calendar = Calendar.getInstance(timeZone);
            Date currentTime = calendar.getTime();
            Optional<DesignStyleEntity> designStyleEntity = designStyleRepository.findById(designId);
            if(designStyleEntity.isEmpty()){
                return false;
            }else{
                if(name != null ){
                    designStyleEntity.get().setName(name);
                }

                if(description != null ){
                    designStyleEntity.get().setDescription(description);
                }

                if(file != null){
                    String img = fireBaseStorageService.uploadImage(file);
                    designStyleEntity.get().setImg(img);
                }
                designStyleEntity.get().setUpdatedAt(currentTime);

                if(status != null ){
                    designStyleEntity.get().setStatus(status);
                }

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
