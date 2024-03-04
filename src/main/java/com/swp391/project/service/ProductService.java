package com.swp391.project.service;

import com.swp391.project.dto.ProductDTO;
import com.swp391.project.dto.UserDetailDTO;
import com.swp391.project.entity.DesignStyleEntity;
import com.swp391.project.entity.ProductEntity;
import com.swp391.project.entity.UserEntity;
import com.swp391.project.payload.request.ProductRequest;
import com.swp391.project.repository.ProductRepository;
import com.swp391.project.service.impl.ProductServiceImp;
import com.swp391.project.service.impl.ProjectServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.TimeZone;

@Service
public class ProductService implements ProductServiceImp {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private FireBaseStorageService fireBaseStorageService;

    @Override
    public boolean create(String name,
                          String description,
                          String type,
                          double height,
                          double length,
                          double width,
                          double pricePerM2,
                          MultipartFile imgFile) {
        try{
            TimeZone timeZone = TimeZone.getTimeZone("Asia/Ho_Chi_Minh");

            // Lấy thời gian hiện tại dựa trên múi giờ của Việt Nam
            Calendar calendar = Calendar.getInstance(timeZone);
            Date currentTime = calendar.getTime();
            ProductEntity productEntity = new ProductEntity();
            productEntity.setName(name);
            productEntity.setDescription(description);
            productEntity.setHeight(height);
            productEntity.setLength(length);
            productEntity.setWidth(width);
            productEntity.setPricePerM2(pricePerM2);
            productEntity.setType(type);
            productEntity.setStatus("ACTIVE");
            if(imgFile != null){
                String img = fireBaseStorageService.uploadImage(imgFile);
                productEntity.setImage(img);
            }

            productEntity.setCreatedAt(currentTime);
            productEntity.setUpdatedAt(currentTime);
            productRepository.save(productEntity);
            return true;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean update(String name,
                          String description,
                          String type,
                          double height,
                          double length,
                          double width,
                          double pricePerM2, MultipartFile imgFile, int productId) {
        try{
            TimeZone timeZone = TimeZone.getTimeZone("Asia/Ho_Chi_Minh");

            // Lấy thời gian hiện tại dựa trên múi giờ của Việt Nam
            Calendar calendar = Calendar.getInstance(timeZone);
            Date currentTime = calendar.getTime();
            Optional<ProductEntity> productEntity = productRepository.findById(productId);
            if(productEntity.isEmpty()){
                return false;
            }else{

                if(imgFile != null){
                    String img = fireBaseStorageService.uploadImage(imgFile);
                    productEntity.get().setImage(img);
                }
                if(name != null){
                    productEntity.get().setName(name);
                }
                if(description != null){
                    productEntity.get().setDescription(description);
                }

                if(type != null){
                    productEntity.get().setType(type);
                }
                if(height != productEntity.get().getHeight() && !Double.isNaN(height)){
                    productEntity.get().setHeight(height);
                }

                if(length != productEntity.get().getLength() && !Double.isNaN(length)) {
                    productEntity.get().setLength(length);
                }

                if(width != productEntity.get().getWidth() && !Double.isNaN(width)) {
                    productEntity.get().setWidth(width);
                }

                if(pricePerM2 != productEntity.get().getPricePerM2() && !Double.isNaN(pricePerM2)) {
                    productEntity.get().setPricePerM2(pricePerM2);
                }

                productRepository.save(productEntity.get());

                return true;
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }

    }

    @Override
    public Page<ProductDTO> findAll(Pageable pageable) {
        Page<ProductEntity> productsPage  = productRepository.findAll(pageable);
        return productsPage.map(productEntity -> new ProductDTO(
                productEntity.getName(),
                productEntity.getImage(),
                productEntity.getDescription(),
                productEntity.getType(),
                productEntity.getHeight(),
                productEntity.getLength(),
                productEntity.getWidth(),
                productEntity.getPricePerM2(),
                productEntity.getCreatedAt(),
                productEntity.getUpdatedAt(),
                productEntity.getStatus()
        ));
    }

    public  ProductDTO mapProductToDTO(ProductEntity productEntity) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName(productEntity.getName());
        productDTO.setImg(productEntity.getImage());
        productDTO.setDescription(productEntity.getDescription());
        productDTO.setHeight(productEntity.getHeight());
        productDTO.setLength(productEntity.getLength());
        productDTO.setWidth(productEntity.getWidth());
        productDTO.setPricePerM2(productEntity.getPricePerM2());
        productDTO.setCreatedAt(productEntity.getCreatedAt());
        productDTO.setUpdatedAt(productEntity.getUpdatedAt());
        productDTO.setStatus(productEntity.getStatus());
        return productDTO;
    }
}