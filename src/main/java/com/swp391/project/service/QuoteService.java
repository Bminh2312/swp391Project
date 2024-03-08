package com.swp391.project.service;


import com.swp391.project.dto.*;
import com.swp391.project.entity.*;
import com.swp391.project.payload.request.QuoteRequest;
import com.swp391.project.repository.ProjectRepository;
import com.swp391.project.repository.QuoteDetailRepository;
import com.swp391.project.repository.QuoteRepository;
import com.swp391.project.repository.RoomRepository;
import com.swp391.project.service.impl.QuoteServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class QuoteService implements QuoteServiceImp {

    @Autowired
    private QuoteRepository quoteRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private QuoteDetailRepository quoteDetailRepository;

    @Override
    public boolean create(QuoteRequest quoteRequest, String status) {
        try{
            Optional<ProjectEntity> projectEntity = projectRepository.findById(quoteRequest.getProjectId());
            Optional<RoomEntity> roomEntity = roomRepository.findById(quoteRequest.getRoomId());
            if(projectEntity.isPresent() && roomEntity.isPresent()){
                TimeZone timeZone = TimeZone.getTimeZone("Asia/Ho_Chi_Minh");

                // Lấy thời gian hiện tại dựa trên múi giờ của Việt Nam
                Calendar calendar = Calendar.getInstance(timeZone);
                Date currentTime = calendar.getTime();
                QuoteEntity quoteEntity = new QuoteEntity();
                quoteEntity.setProjectQuote(projectEntity.get());
                quoteEntity.setRoomQuote(roomEntity.get());
                quoteEntity.setQuoteDate(currentTime);
                quoteEntity.setCreatedAt(currentTime);
                quoteEntity.setUpdatedAt(currentTime);
                quoteEntity.setStatus(status);
                quoteRepository.save(quoteEntity);
                return true;
            }else {
                return false;
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }

    }

    @Override
    public boolean update(QuoteRequest quoteRequest, int id, String status) {
        try{
            Optional<QuoteEntity> quoteEntity = quoteRepository.findById(id);
            if(quoteEntity.isPresent()){
                TimeZone timeZone = TimeZone.getTimeZone("Asia/Ho_Chi_Minh");

                // Lấy thời gian hiện tại dựa trên múi giờ của Việt Nam
                Calendar calendar = Calendar.getInstance(timeZone);
                Date currentTime = calendar.getTime();
                Optional<ProjectEntity> projectEntity = projectRepository.findById(quoteRequest.getProjectId());
                Optional<RoomEntity> roomEntity = roomRepository.findById(quoteRequest.getRoomId());
                if(projectEntity.isPresent() && quoteEntity.get().getProjectQuote() != projectEntity.get()){
                    quoteEntity.get().setProjectQuote(projectEntity.get());
                }

                if(roomEntity.isPresent() && quoteEntity.get().getRoomQuote() != roomEntity.get()){
                    quoteEntity.get().setRoomQuote(roomEntity.get());
                }
                quoteEntity.get().setUpdatedAt(currentTime);
                if(quoteEntity.get().getStatus()!= null && !status.equals(quoteEntity.get().getStatus())){
                    quoteEntity.get().setStatus(status);
                }
                quoteRepository.save(quoteEntity.get());
                return true;
            }else {
                return false;
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }

    }

    @Transactional(rollbackFor = {RuntimeException.class,Exception.class})
    @Override
    public boolean updateTotal(int projectId) {
        try{
            double total = 0;
            Optional<ProjectEntity> projectEntity = projectRepository.findById(projectId);
            if (projectEntity.isPresent()) {
                List<QuoteEntity> quotes = quoteRepository.findByProjectQuoteId(projectEntity.get().getId());
                for (QuoteEntity quoteEntity : quotes) {
                    total = 0;
                    List<QuoteDetailEntity> quoteDetails = quoteDetailRepository.findByQuoteId(quoteEntity.getId());
                    for (QuoteDetailEntity quoteDetail : quoteDetails) {
                        total +=quoteDetail.getPrice();
                    }
                    quoteEntity.setTotal(total);
                    quoteRepository.save(quoteEntity);
                }
                return true;
            }
            return false;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }


    @Transactional(rollbackFor = {RuntimeException.class,Exception.class})
    @Override
    public ProjectWithAllQuoteDTO findAllQuoteRoomByProject(int projectId) {
        ProjectWithAllQuoteDTO projectWithAllQuote = new ProjectWithAllQuoteDTO();
        List<RoomWithAllQuoteDetailDTO> roomWithAllQuoteDetailDTOs = new ArrayList<>();
        try {
            Optional<ProjectEntity> projectEntityOptional = projectRepository.findById(projectId);
            if (projectEntityOptional.isPresent()) {
                ProjectEntity projectEntity = projectEntityOptional.get();

                ProjectDTO projectDTO = mapProjectToDTO(projectEntity);
                List<QuoteEntity> quotes = quoteRepository.findByProjectQuoteId(projectEntity.getId());

                for (QuoteEntity quoteEntity : quotes) {
                    RoomWithAllQuoteDetailDTO roomWithAllQuoteDetailDTO = new RoomWithAllQuoteDetailDTO();
                    roomWithAllQuoteDetailDTO.setRoomName(quoteEntity.getRoomQuote().getName());
                    List<QuoteDetailEntity> quoteDetails = quoteDetailRepository.findByQuoteId(quoteEntity.getId());
                    List<QuoteDetailDTO> quoteDetailDTOs = new ArrayList<>();
                    double total = 0;
                    for (QuoteDetailEntity quoteDetail : quoteDetails) {
                        QuoteDetailDTO quoteDetailDTO = getQuoteDetailDTO(quoteDetail);
                        total +=quoteDetail.getPrice();
                        quoteDetailDTOs.add(quoteDetailDTO);
                    }
                    roomWithAllQuoteDetailDTO.setQuoteDetailDTOS(quoteDetailDTOs);
                    roomWithAllQuoteDetailDTO.setTotal(total);
                    roomWithAllQuoteDetailDTOs.add(roomWithAllQuoteDetailDTO);

                }

                projectWithAllQuote.setProjectDTO(projectDTO);
                projectWithAllQuote.setWithAllQuoteDetailDTOList(roomWithAllQuoteDetailDTOs);
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Handle any exceptions appropriately
        }
        return projectWithAllQuote;
    }

    public RawMaterialDTO mapRawMaterialToDTO(RawMaterialEntity rawMaterialEntity) {
        RawMaterialDTO rawMaterialDTO = new RawMaterialDTO();
        rawMaterialDTO.setId(rawMaterialEntity.getId());
        rawMaterialDTO.setName(rawMaterialEntity.getName());
        rawMaterialDTO.setImg(rawMaterialEntity.getImg());
        rawMaterialDTO.setDescription(rawMaterialEntity.getDescription());
        rawMaterialDTO.setType(rawMaterialEntity.getType());
        rawMaterialDTO.setPricePerM2(rawMaterialEntity.getPricePerM2());
        rawMaterialDTO.setCreatedAt(rawMaterialEntity.getCreatedAt());
        rawMaterialDTO.setUpdatedAt(rawMaterialEntity.getUpdatedAt());
        rawMaterialDTO.setStatus(rawMaterialEntity.getStatus());
        // Không map trường quoteDetailDTOList vì nó không phải là một trường của RawMaterialEntity

        return rawMaterialDTO;
    }

    public  ProductDTO mapProductToDTO(ProductEntity productEntity) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(productEntity.getId());
        productDTO.setName(productEntity.getName());
        productDTO.setImg(productEntity.getImage());
        productDTO.setDescription(productEntity.getDescription());
        productDTO.setHeight(productEntity.getHeight());
        productDTO.setLength(productEntity.getLength());
        productDTO.setWidth(productEntity.getWidth());
        productDTO.setPrice(productEntity.getPrice());
        productDTO.setCreatedAt(productEntity.getCreatedAt());
        productDTO.setUpdatedAt(productEntity.getUpdatedAt());
        productDTO.setStatus(productEntity.getStatus());
        return productDTO;
    }

    private QuoteDetailDTO getQuoteDetailDTO(QuoteDetailEntity quoteDetail) {
        QuoteDetailDTO quoteDetailDTO = new QuoteDetailDTO();
        quoteDetailDTO.setId(quoteDetail.getId());
        quoteDetailDTO.setQuantity(quoteDetail.getQuantity());
        if(quoteDetail.getProduct() != null){
            quoteDetailDTO.setProduct(mapProductToDTO(quoteDetail.getProduct()));
        }
        if(quoteDetail.getRawMaterial() != null){
            quoteDetailDTO.setRawMaterial(mapRawMaterialToDTO(quoteDetail.getRawMaterial()));
        }
        quoteDetailDTO.setPrice(quoteDetail.getPrice());
        quoteDetailDTO.setArea(quoteDetail.getArea());
        quoteDetailDTO.setNote(quoteDetail.getNote());
        quoteDetailDTO.setCreatedAt(quoteDetail.getCreatedAt());
        quoteDetailDTO.setUpdatedAt(quoteDetail.getUpdatedAt());
        quoteDetailDTO.setStatus(quoteDetail.getStatus());
        return quoteDetailDTO;
    }

    public  ProjectDTO mapProjectToDTO(ProjectEntity projectEntity) {
        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setId(projectEntity.getId());
        projectDTO.setName(projectEntity.getName());
        projectDTO.setLocation(projectEntity.getLocation());
        projectDTO.setSample(projectEntity.isSample());
        projectDTO.setDesignStyleName(projectEntity.getDesignStyle().getName());
        projectDTO.setType(projectEntity.getType());
        projectDTO.setCreatedAt(projectEntity.getCreatedAt());
        projectDTO.setUpdatedAt(projectEntity.getUpdatedAt());
        projectDTO.setStatus(projectEntity.getStatus());
        return projectDTO;
    }



}
