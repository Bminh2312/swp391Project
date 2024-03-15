package com.swp391.project.service;

import com.swp391.project.dto.*;
import com.swp391.project.entity.*;
import com.swp391.project.payload.request.ProjectRequest;
import com.swp391.project.repository.*;

import com.swp391.project.service.impl.ProjectServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProjectService implements ProjectServiceImp {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private QuoteRepository quoteRepository;

    @Autowired
    private QuoteDetailRepository quoteDetailRepository;

    @Autowired
    private DesignStyleRepository designStyleRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public int create(ProjectRequest projectRequest , int userId, String status) {
        try{
            Optional<DesignStyleEntity> designStyleEntity = designStyleRepository.findById(projectRequest.getDesignStyleId());
            if(designStyleEntity.isPresent()){
                TimeZone timeZone = TimeZone.getTimeZone("Asia/Ho_Chi_Minh");

                // Lấy thời gian hiện tại dựa trên múi giờ của Việt Nam
                Calendar calendar = Calendar.getInstance(timeZone);
                Date currentTime = calendar.getTime();
                ProjectEntity projectEntity = new ProjectEntity();
                Optional<UserEntity> userEntity = userRepository.findById(userId);
                userEntity.ifPresent(projectEntity::setUser);
                projectEntity.setName(projectRequest.getName());
                projectEntity.setLocation(projectRequest.getLocation());
                projectEntity.setType(projectRequest.getType());
                projectEntity.setDesignStyle(designStyleEntity.get());
                projectEntity.setSample(false);
                projectEntity.setStatus(status);
                projectEntity.setCreatedAt(currentTime);
                projectEntity.setUpdatedAt(currentTime);
                ProjectEntity projectEntityRespone =  projectRepository.save(projectEntity);
                return projectEntityRespone.getId();
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            return 0;
        }

        return 0;
    }



    @Override
    public boolean update(ProjectRequest projectRequest,int projectId) {
        try{
            Optional<ProjectEntity> projectEntity = projectRepository.findById(projectId);
            Optional<DesignStyleEntity> designStyleEntity = designStyleRepository.findById(projectRequest.getDesignStyleId());
            if(projectEntity.isPresent()){
                TimeZone timeZone = TimeZone.getTimeZone("Asia/Ho_Chi_Minh");

                // Lấy thời gian hiện tại dựa trên múi giờ của Việt Nam
                Calendar calendar = Calendar.getInstance(timeZone);
                Date currentTime = calendar.getTime();
                if(projectRequest.getName() != null){
                    projectEntity.get().setName(projectRequest.getName());
                }
                 if(projectRequest.getLocation() != null){
                     projectEntity.get().setLocation(projectRequest.getLocation());
                 }

                 if(projectRequest.getType() != null ){
                     projectEntity.get().setType(projectRequest.getType());
                 }
                designStyleEntity.ifPresent(styleEntity -> projectEntity.get().setDesignStyle(styleEntity));
                projectEntity.get().setUpdatedAt(currentTime);
                projectRepository.save(projectEntity.get());
                return true;
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
        return false;
    }

    @Override
    public boolean updateProjectByStatus(int projectId, String status) {
        try{
            Optional<ProjectEntity> projectEntity = projectRepository.findById(projectId);
            if(projectEntity.isPresent()){
                TimeZone timeZone = TimeZone.getTimeZone("Asia/Ho_Chi_Minh");

                // Lấy thời gian hiện tại dựa trên múi giờ của Việt Nam
                Calendar calendar = Calendar.getInstance(timeZone);
                Date currentTime = calendar.getTime();
                projectEntity.get().setStatus(status);
                projectEntity.get().setUpdatedAt(currentTime);
                projectRepository.save(projectEntity.get());
                return true;
            }
            return false;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }

    }


    @Override
    public ProjectDTO findById(int id) {
        Optional<ProjectEntity> projectEntity = projectRepository.findById(id);
        ProjectDTO projectDTO = new ProjectDTO();
        if(projectEntity.isPresent()){
            projectDTO.setId(projectEntity.get().getId());
            projectDTO.setName(projectEntity.get().getName());
            projectDTO.setDesignStyleName(projectEntity.get().getDesignStyle().getName());
            projectDTO.setLocation(projectEntity.get().getLocation());
            projectDTO.setType(projectEntity.get().getType());
            projectDTO.setSample(projectEntity.get().isSample());
            projectDTO.setCreatedAt(projectEntity.get().getCreatedAt());
            projectDTO.setUpdatedAt(projectEntity.get().getUpdatedAt());
            projectDTO.setStatus(projectEntity.get().getStatus());
            return projectDTO;
        }
        return null;
    }

    @Override
    public List<ProjectDTO> findByStatus(String status) {
        List<ProjectEntity> projectEntities = projectRepository.findByStatus(status);
        List<ProjectDTO> projectDTOS = new ArrayList<>();
        if(!projectEntities.isEmpty()){
            for (ProjectEntity projectEntity: projectEntities) {
                ProjectDTO projectDTO = new ProjectDTO();
                projectDTO.setId(projectEntity.getId());
                if(projectEntity.getUser() != null){
                    projectDTO.setUserDetailDTO(mapUserEntityToDTO(projectEntity.getUser()));
                }
                projectDTO.setName(projectEntity.getName());
                projectDTO.setDesignStyleName(projectEntity.getDesignStyle().getName());
                projectDTO.setLocation(projectEntity.getLocation());
                projectDTO.setType(projectEntity.getType());
                projectDTO.setCreatedAt(projectEntity.getCreatedAt());
                projectDTO.setUpdatedAt(projectEntity.getUpdatedAt());
                projectDTO.setStatus(projectEntity.getStatus());
                projectDTO.setSample(projectEntity.isSample());
                projectDTOS.add(projectDTO);
            }
            return projectDTOS;
        }
        return null;
    }

    @Override
    public Page<ProjectWithUserDTO> findAllByStatus(String status, Pageable pageable) {
        Page<ProjectEntity> projectEntityPage;
        if (status != null) {
            projectEntityPage = projectRepository.findAllByStatus(status, pageable);
        } else {
            projectEntityPage = projectRepository.findAll(pageable);
        }

        return projectEntityPage.map(projectEntity -> new ProjectWithUserDTO(
                projectEntity.getId(),
                projectEntity.getName(),
                projectEntity.getImg(),
                projectEntity.getLocation(),
                projectEntity.getType(),
                projectEntity.isSample(),
                projectEntity.getDesignStyle().getName(),
                projectEntity.getCreatedAt(),
                projectEntity.getUpdatedAt(),
                projectEntity.getStatus()
        ));
    }

    @Override
    public Page<ProjectDTO> findAllByStatusAndUserId(int userId, String status, Pageable pageable) {
        UserEntity userEntity = userRepository.findById(userId).orElse(null);
        if (userEntity == null) {
            // Xử lý trường hợp không tìm thấy người dùng
            return null;
        }
        Page<ProjectEntity> projectEntityPage;
        if (status != null) {
            projectEntityPage = projectRepository.findAllByStatusAndUserId(status, userId, pageable);
        } else {
            projectEntityPage = projectRepository.findAllByUserId(userId, pageable);
        }

        return projectEntityPage.map(projectEntity -> new ProjectDTO(
                mapUserToDTO(projectEntity.getUser()),
                projectEntity.getId(),
                projectEntity.getName(),
                projectEntity.getImg(),
                projectEntity.getLocation(),
                projectEntity.getType(),
                projectEntity.isSample(),
                projectEntity.getDesignStyle().getName(),
                projectEntity.getCreatedAt(),
                projectEntity.getUpdatedAt(),
                projectEntity.getStatus()
        ));
    }


    @Override
    public  List<ProjectDTO> findByStatusAndUserId(String status,int id) {
        List<ProjectEntity> projectEntities = projectRepository.findByStatusAndUserId(status,id);
        List<ProjectDTO> projectDTOS = new ArrayList<>();
        if(!projectEntities.isEmpty()){
            for (ProjectEntity projectEntity: projectEntities) {
                ProjectDTO projectDTO = new ProjectDTO();
                projectDTO.setId(projectEntity.getId());
                projectDTO.setName(projectEntity.getName());
                projectDTO.setLocation(projectEntity.getLocation());
                projectDTO.setType(projectEntity.getType());
                projectDTO.setDesignStyleName(projectEntity.getDesignStyle().getName());
                projectDTO.setCreatedAt(projectEntity.getCreatedAt());
                projectDTO.setUpdatedAt(projectEntity.getUpdatedAt());
                projectDTO.setStatus(projectEntity.getStatus());
                projectDTO.setSample(projectEntity.isSample());
                projectDTOS.add(projectDTO);
            }
            return projectDTOS;
        }
        return null;
    }

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
                    roomWithAllQuoteDetailDTO.setImg(quoteEntity.getImg());
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

    public ProjectDTO mapProjectAndUserToDTO(ProjectEntity projectEntity) {
        if (projectEntity == null) {
            return null;
        }

        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setId(projectEntity.getId());
        projectDTO.setName(projectEntity.getName());
        projectDTO.setLocation(projectEntity.getLocation());
        projectDTO.setType(projectEntity.getType());
        projectDTO.setSample(projectEntity.isSample());
        projectDTO.setDesignStyleName(projectEntity.getDesignStyle().getName());
        projectDTO.setCreatedAt(projectEntity.getCreatedAt());
        projectDTO.setUpdatedAt(projectEntity.getUpdatedAt());
        projectDTO.setStatus(projectEntity.getStatus());

        // Tạo đối tượng UserDetailDTO và thiết lập thông tin người dùng
        UserDetailDTO userDetailDTO = new UserDetailDTO();
        userDetailDTO.setId(projectEntity.getUser().getId());
        userDetailDTO.setFullName(projectEntity.getUser().getFullName());
        userDetailDTO.setEmail(projectEntity.getUser().getEmail());
        userDetailDTO.setAvt(projectEntity.getUser().getAvt());
        userDetailDTO.setAccessToken(projectEntity.getUser().getAccessToken());
        userDetailDTO.setRole(projectEntity.getUser().getRole().getName());

        // Thiết lập thông tin người dùng vào ProjectDTO
        projectDTO.setUserDetailDTO(userDetailDTO);

        return projectDTO;
    }

    public UserDetailDTO mapUserEntityToDTO(UserEntity userEntity) {
        UserDetailDTO userDetailDTO = new UserDetailDTO();
        userDetailDTO.setId(userEntity.getId());
        userDetailDTO.setFullName(userEntity.getFullName());
        userDetailDTO.setEmail(userEntity.getEmail());
        userDetailDTO.setAvt(userEntity.getAvt());
        userDetailDTO.setAccessToken(userEntity.getAccessToken());
        userDetailDTO.setRole(userEntity.getRole().getName()); // Assumed role has a name attribute

        return userDetailDTO;
    }

    public  ProjectDTO mapProjectToDTO(ProjectEntity projectEntity) {
        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setId(projectEntity.getId());
        projectDTO.setName(projectEntity.getName());
        projectDTO.setImg(projectEntity.getImg());
        projectDTO.setLocation(projectEntity.getLocation());
        projectDTO.setSample(projectEntity.isSample());
        projectDTO.setDesignStyleName(projectEntity.getDesignStyle().getName());
        projectDTO.setType(projectEntity.getType());
        projectDTO.setCreatedAt(projectEntity.getCreatedAt());
        projectDTO.setUpdatedAt(projectEntity.getUpdatedAt());
        projectDTO.setStatus(projectEntity.getStatus());
        return projectDTO;
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

    private UserDetailDTO mapUserToDTO(UserEntity userEntity) {
        UserDetailDTO userDTO = new UserDetailDTO();
        // Thực hiện ánh xạ các trường từ entity sang DTO
        userDTO.setId(userEntity.getId());
        userDTO.setFullName(userEntity.getFullName());
        userDTO.setAvt(userEntity.getAvt());
        userDTO.setEmail(userEntity.getEmail());
        return userDTO;
    }


}
