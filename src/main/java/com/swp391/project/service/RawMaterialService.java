package com.swp391.project.service;

import com.swp391.project.dto.ProductDTO;
import com.swp391.project.dto.RawMaterialDTO;
import com.swp391.project.entity.ProductEntity;
import com.swp391.project.entity.RawMaterialEntity;
import com.swp391.project.repository.ProductRepository;
import com.swp391.project.repository.RawMaterialRepository;
import com.swp391.project.service.impl.RawMaterialServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.TimeZone;

@Service
public class RawMaterialService implements RawMaterialServiceImp {

    @Autowired
    private RawMaterialRepository rawMaterialRepository;

    @Autowired
    private FireBaseStorageService fireBaseStorageService;
    @Override
    public boolean create(String name, String description, String type, double pricePerM2, MultipartFile imgFile) {
        try{
            TimeZone timeZone = TimeZone.getTimeZone("Asia/Ho_Chi_Minh");

            // Lấy thời gian hiện tại dựa trên múi giờ của Việt Nam
            Calendar calendar = Calendar.getInstance(timeZone);
            Date currentTime = calendar.getTime();
            RawMaterialEntity rawMaterial = new RawMaterialEntity();
            rawMaterial.setName(name);
            rawMaterial.setDescription(description);
            rawMaterial.setPricePerM2(pricePerM2);
            rawMaterial.setType(type);
            rawMaterial.setStatus("ACTIVE");
            if(imgFile != null){
                String img = fireBaseStorageService.uploadImage(imgFile);
                rawMaterial.setImg(img);
            }

            rawMaterial.setCreatedAt(currentTime);
            rawMaterial.setUpdatedAt(currentTime);
            rawMaterialRepository.save(rawMaterial);
            return true;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean update(String name, String description, String type, double pricePerM2, MultipartFile imgFile, int rawMaterialId) {
        try{
            TimeZone timeZone = TimeZone.getTimeZone("Asia/Ho_Chi_Minh");

            // Lấy thời gian hiện tại dựa trên múi giờ của Việt Nam
            Calendar calendar = Calendar.getInstance(timeZone);
            Date currentTime = calendar.getTime();
            Optional<RawMaterialEntity> rawMaterialEntity = rawMaterialRepository.findById(rawMaterialId);
            if(rawMaterialEntity.isEmpty()){
                return false;
            }else{

                if(imgFile != null){
                    String img = fireBaseStorageService.uploadImage(imgFile);
                    rawMaterialEntity.get().setImg(img);
                }
                if(name != null){
                    rawMaterialEntity.get().setName(name);
                }
                if(description != null){
                    rawMaterialEntity.get().setDescription(description);
                }

                if(type != null){
                    rawMaterialEntity.get().setType(type);
                }


                if(pricePerM2 != rawMaterialEntity.get().getPricePerM2() && !Double.isNaN(pricePerM2)) {
                    rawMaterialEntity.get().setPricePerM2(pricePerM2);
                }



                rawMaterialRepository.save(rawMaterialEntity.get());

                return true;
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public Page<RawMaterialDTO> findAll(Pageable pageable) {
        // Lấy trang nguyên liệu từ cơ sở dữ liệu
        Page<RawMaterialEntity> rawMaterialsPage = rawMaterialRepository.findAll(pageable);

        // Chuyển đổi từng nguyên liệu trong trang từ RawMaterialEntity sang RawMaterialDTO
        return rawMaterialsPage.map(rawMaterialEntity -> {
            RawMaterialDTO rawMaterialDTO = new RawMaterialDTO();
            rawMaterialDTO.setId(rawMaterialEntity.getId());
            rawMaterialDTO.setName(rawMaterialEntity.getName());
            rawMaterialDTO.setImg(rawMaterialEntity.getImg());
            rawMaterialDTO.setDescription(rawMaterialEntity.getDescription());
            rawMaterialDTO.setType(rawMaterialEntity.getType());
            rawMaterialDTO.setPricePerM2(rawMaterialEntity.getPricePerM2());
            // Set other properties of RawMaterialDTO as needed
            rawMaterialDTO.setCreatedAt(rawMaterialEntity.getCreatedAt());
            rawMaterialDTO.setUpdatedAt(rawMaterialEntity.getUpdatedAt());
            rawMaterialDTO.setStatus(rawMaterialEntity.getStatus());
            return rawMaterialDTO;
        });
    }

    @Override
    public boolean delete(int id, String status) {
        try {
            TimeZone timeZone = TimeZone.getTimeZone("Asia/Ho_Chi_Minh");
            Optional<RawMaterialEntity> rawMaterialEntity = rawMaterialRepository.findById(id);
            if (rawMaterialEntity.isPresent()) {
                // Lấy thời gian hiện tại dựa trên múi giờ của Việt Nam
                Calendar calendar = Calendar.getInstance(timeZone);
                Date currentTime = calendar.getTime();
                rawMaterialEntity.get().setStatus(status);
                rawMaterialEntity.get().setUpdatedAt(currentTime);
                rawMaterialRepository.save(rawMaterialEntity.get());
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
