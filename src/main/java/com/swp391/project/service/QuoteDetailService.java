package com.swp391.project.service;

import com.swp391.project.dto.ProductDTO;
import com.swp391.project.dto.QuoteDetailDTO;
import com.swp391.project.dto.RawMaterialDTO;
import com.swp391.project.entity.*;
import com.swp391.project.payload.request.ListQuoteDetailForProductRequest;
import com.swp391.project.payload.request.ListQuoteDetailForRawRequest;
import com.swp391.project.payload.request.QuoteDetailForProductRequest;
import com.swp391.project.payload.request.QuoteDetailForRawRequest;
import com.swp391.project.repository.ProductRepository;
import com.swp391.project.repository.QuoteDetailRepository;
import com.swp391.project.repository.QuoteRepository;
import com.swp391.project.repository.RawMaterialRepository;
import com.swp391.project.service.impl.QuoteDetailServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class QuoteDetailService implements QuoteDetailServiceImp {

    @Autowired
    private QuoteDetailRepository quoteDetailRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private RawMaterialRepository rawMaterialRepository;

    @Autowired
    private QuoteRepository quoteRepository;

    @Override
    public boolean createQuoteForProduct(QuoteDetailForProductRequest quoteDetailRequest) {
        try{
            Optional<ProductEntity> productEntity = productRepository.findById(quoteDetailRequest.getProductId());
            Optional<QuoteEntity> quoteEntity = quoteRepository.findById(quoteDetailRequest.getQuoteId());
            if(productEntity.isPresent()){
                TimeZone timeZone = TimeZone.getTimeZone("Asia/Ho_Chi_Minh");

                // Lấy thời gian hiện tại dựa trên múi giờ của Việt Nam
                Calendar calendar = Calendar.getInstance(timeZone);
                Date currentTime = calendar.getTime();
                QuoteDetailEntity quoteDetailEntity = new QuoteDetailEntity();
                quoteDetailEntity.setProduct(productEntity.get());
                quoteDetailEntity.setQuantity(quoteDetailRequest.getQuantity());
                if(quoteDetailRequest.getPriceChange() == 0){
                    quoteDetailEntity.setTotalPrice(quoteDetailRequest.getQuantity() * productEntity.get().getPrice());

                }
                quoteDetailRequest.setPriceChange(0);
                quoteDetailEntity.setNote(quoteDetailRequest.getNote());
                quoteDetailEntity.setArea(0);
                quoteEntity.ifPresent(quoteDetailEntity::setQuote);
                quoteDetailEntity.setStatus("ACTIVE");
                quoteDetailEntity.setCreatedAt(currentTime);
                quoteDetailEntity.setUpdatedAt(currentTime);
                quoteDetailRepository.save(quoteDetailEntity);
                return true;
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }

        return false;
    }

    @Transactional(rollbackFor = {RuntimeException.class,Exception.class})
    @Override
    public boolean createListQuoteForProduct(ListQuoteDetailForProductRequest listQuoteDetailForProductRequest) {
        try {
            List<QuoteDetailForProductRequest> quoteDetailRequests = listQuoteDetailForProductRequest.getQuoteDetailForProductRequests();

            for (QuoteDetailForProductRequest quoteDetailRequest : quoteDetailRequests) {
                Optional<ProductEntity> productEntity = productRepository.findById(quoteDetailRequest.getProductId());
                Optional<QuoteEntity> quoteEntity = quoteRepository.findById(quoteDetailRequest.getQuoteId());

                if (productEntity.isPresent()) {
                    TimeZone timeZone = TimeZone.getTimeZone("Asia/Ho_Chi_Minh");

                    // Lấy thời gian hiện tại dựa trên múi giờ của Việt Nam
                    Calendar calendar = Calendar.getInstance(timeZone);
                    Date currentTime = calendar.getTime();

                    QuoteDetailEntity quoteDetailEntity = new QuoteDetailEntity();
                    quoteDetailEntity.setProduct(productEntity.get());
                    quoteDetailEntity.setQuantity(quoteDetailRequest.getQuantity());

                    if (quoteDetailRequest.getPriceChange() == 0) {
                        quoteDetailEntity.setTotalPrice(quoteDetailRequest.getQuantity() * productEntity.get().getPrice());
                    }

                    quoteDetailRequest.setPriceChange(0);
                    quoteDetailEntity.setNote(quoteDetailRequest.getNote());
                    quoteDetailEntity.setArea(0);
                    quoteEntity.ifPresent(quoteDetailEntity::setQuote);
                    quoteDetailEntity.setStatus("ACTIVE");
                    quoteDetailEntity.setCreatedAt(currentTime);
                    quoteDetailEntity.setUpdatedAt(currentTime);
                    quoteDetailRepository.save(quoteDetailEntity);
                }
            }
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }


//    @Override
//    public boolean createQuoteForProductByUser(QuoteDetailRequest quoteDetailRequest) {
//        try{
//            Optional<ProductEntity> productEntity = productRepository.findById(quoteDetailRequest.getProductId());
//            if(productEntity.isPresent()){
//                TimeZone timeZone = TimeZone.getTimeZone("Asia/Ho_Chi_Minh");
//
//                // Lấy thời gian hiện tại dựa trên múi giờ của Việt Nam
//                Calendar calendar = Calendar.getInstance(timeZone);
//                Date currentTime = calendar.getTime();
//                QuoteDetailEntity quoteDetailEntity = new QuoteDetailEntity();
//                quoteDetailEntity.setProduct(productEntity.get());
//                quoteDetailEntity.setQuantity(quoteDetailRequest.getQuantity());
//                quoteDetailEntity.setPrice(quoteDetailRequest.getQuantity() * productEntity.get().getPrice());
//                quoteDetailEntity.setNote(quoteDetailRequest.getNote());
//                quoteDetailEntity.setArea(0);
//                quoteDetailEntity.setStatus("ACTIVE");
//                quoteDetailEntity.setCreatedAt(currentTime);
//                quoteDetailEntity.setUpdatedAt(currentTime);
//                quoteDetailRepository.save(quoteDetailEntity);
//                return true;
//            }
//        }catch (Exception e){
//            System.out.println(e.getMessage());
//            return false;
//        }
//
//        return false;
//
//    }

    @Override
    public boolean createQuoteForRaw(QuoteDetailForRawRequest quoteDetailRequest) {
        try{
            Optional<RawMaterialEntity> rawMaterialEntity = rawMaterialRepository.findById(quoteDetailRequest.getRawMaterialId());
            Optional<QuoteEntity> quoteEntity = quoteRepository.findById(quoteDetailRequest.getQuoteId());
            if(rawMaterialEntity.isPresent()){
                TimeZone timeZone = TimeZone.getTimeZone("Asia/Ho_Chi_Minh");

                // Lấy thời gian hiện tại dựa trên múi giờ của Việt Nam
                Calendar calendar = Calendar.getInstance(timeZone);
                Date currentTime = calendar.getTime();
                QuoteDetailEntity quoteDetailEntity = new QuoteDetailEntity();
                quoteDetailEntity.setRawMaterial(rawMaterialEntity.get());
                quoteDetailEntity.setQuantity(0);
                quoteDetailEntity.setPriceChange(0);
                quoteDetailEntity.setArea(quoteDetailRequest.getArea());
                quoteDetailEntity.setTotalPrice(quoteDetailRequest.getArea() * rawMaterialEntity.get().getPricePerM2());
                quoteEntity.ifPresent(quoteDetailEntity::setQuote);
                quoteDetailEntity.setStatus("ACTIVE");
                quoteDetailEntity.setCreatedAt(currentTime);
                quoteDetailEntity.setUpdatedAt(currentTime);
                quoteDetailRepository.save(quoteDetailEntity);
                return true;
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }

        return false;
    }


    @Transactional(rollbackFor = {RuntimeException.class,Exception.class})
    @Override
    public boolean createListQuoteForRaw(ListQuoteDetailForRawRequest listQuoteDetailForRawRequest) {
        try {
            List<QuoteDetailForRawRequest> quoteDetailRequests = listQuoteDetailForRawRequest.getQuoteDetailForRawRequests();
            List<QuoteDetailEntity> quoteDetailEntities = new ArrayList<>();

            for (QuoteDetailForRawRequest quoteDetailRequest : quoteDetailRequests) {
                Optional<RawMaterialEntity> rawMaterialEntity = rawMaterialRepository.findById(quoteDetailRequest.getRawMaterialId());
                Optional<QuoteEntity> quoteEntity = quoteRepository.findById(quoteDetailRequest.getQuoteId());

                if (rawMaterialEntity.isPresent() && quoteEntity.isPresent()) {
                    TimeZone timeZone = TimeZone.getTimeZone("Asia/Ho_Chi_Minh");

                    // Lấy thời gian hiện tại dựa trên múi giờ của Việt Nam
                    Calendar calendar = Calendar.getInstance(timeZone);
                    Date currentTime = calendar.getTime();

                    QuoteDetailEntity quoteDetailEntity = new QuoteDetailEntity();
                    quoteDetailEntity.setRawMaterial(rawMaterialEntity.get());
                    quoteDetailEntity.setQuantity(0);
                    quoteDetailEntity.setPriceChange(0);
                    quoteDetailEntity.setArea(quoteDetailRequest.getArea());
                    quoteDetailEntity.setTotalPrice(quoteDetailRequest.getArea() * rawMaterialEntity.get().getPricePerM2());
                    quoteDetailEntity.setQuote(quoteEntity.get());
                    quoteDetailEntity.setStatus("ACTIVE");
                    quoteDetailEntity.setCreatedAt(currentTime);
                    quoteDetailEntity.setUpdatedAt(currentTime);
                    quoteDetailEntities.add(quoteDetailEntity);
                }
            }

            // Lưu danh sách các QuoteDetailEntity vào cơ sở dữ liệu
            quoteDetailRepository.saveAll(quoteDetailEntities);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }


    @Override
    public boolean updateQuoteForProduct(int idQuoteDetail, int idProduct, String note, double priceChange,  int quantityChange) {
        try{
            int quantity = 0;
            double price = 0;
            Optional<QuoteDetailEntity> quoteDetailEntity = quoteDetailRepository.findById(idQuoteDetail);
            if(quoteDetailEntity.isPresent()) {
                Optional<ProductEntity> productEntity = productRepository.findById(quoteDetailEntity.get().getProduct().getId());
                if (productEntity.isPresent()) {
                    quantity = quoteDetailEntity.get().getQuantity();
                    price = productEntity.get().getPrice();
                    TimeZone timeZone = TimeZone.getTimeZone("Asia/Ho_Chi_Minh");

                    // Lấy thời gian hiện tại dựa trên múi giờ của Việt Nam
                    Calendar calendar = Calendar.getInstance(timeZone);
                    Date currentTime = calendar.getTime();
                    Optional<ProductEntity> productEntityChange = productRepository.findById(idProduct);
                    if (idProduct != productEntity.get().getId() && productEntityChange.isPresent()) {
                        quoteDetailEntity.get().setProduct(productEntityChange.get());
                        price = productEntityChange.get().getPrice();
                    }
                    if (quantityChange != quoteDetailEntity.get().getQuantity() && quantityChange != 0) {
                        quoteDetailEntity.get().setQuantity(quantityChange);
                        quantity = quantityChange;
                    }
                    if(priceChange != 0){
                        quoteDetailEntity.get().setPriceChange(priceChange);
                        quoteDetailEntity.get().setTotalPrice(quantity * priceChange);
                    }else{
                        quoteDetailEntity.get().setTotalPrice(quantity * price);
                    }

                    if(note != null){
                        quoteDetailEntity.get().setNote(note);
                    }


                    quoteDetailEntity.get().setArea(0);
                    quoteDetailEntity.get().setUpdatedAt(currentTime);
                    quoteDetailRepository.save(quoteDetailEntity.get());
                    return true;
                }
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
        return false;

    }

//    @Override
//    public boolean updateQuoteForProductByNoteForStaff(int idQuoteDetail, int idProduct, double priceChange, int quantityChange) {
//        try{
//            int quantity = 0;
//            double price = 0;
//            Optional<QuoteDetailEntity> quoteDetailEntity = quoteDetailRepository.findById(idQuoteDetail);
//            if(quoteDetailEntity.isPresent()) {
//                Optional<ProductEntity> productEntity = productRepository.findById(quoteDetailEntity.get().getProduct().getId());
//                System.out.println();
//                if (productEntity.isPresent()) {
//                    quantity = quoteDetailEntity.get().getQuantity();
//                    price = productEntity.get().getPrice();
//                    TimeZone timeZone = TimeZone.getTimeZone("Asia/Ho_Chi_Minh");
//
//                    // Lấy thời gian hiện tại dựa trên múi giờ của Việt Nam
//                    Calendar calendar = Calendar.getInstance(timeZone);
//                    Date currentTime = calendar.getTime();
//                    Optional<ProductEntity> productEntityChange = productRepository.findById(idProduct);
//                    if (idProduct != productEntity.get().getId() && productEntityChange.isPresent()) {
//                        quoteDetailEntity.get().setProduct(productEntityChange.get());
//                        price = productEntityChange.get().getPrice();
//                    }
//                    if (quoteDetailEntity.get().getQuantity() != quantityChange) {
//                        quoteDetailEntity.get().setQuantity(quantityChange);
//                        quantity = quantityChange;
//                    }
//
//                    if ((quoteDetailEntity.get().getNote() != null) && quoteDetailEntity.get().getPrice() != (priceChange * quantity)) {
//                        quoteDetailEntity.get().setPrice(quantity * priceChange);
//                    } else {
//                        quoteDetailEntity.get().setPrice(quantity * price);
//                    }
//
//                    quoteDetailEntity.get().setArea(0);
//                    quoteDetailEntity.get().setUpdatedAt(currentTime);
//                    quoteDetailRepository.save(quoteDetailEntity.get());
//                    return true;
//                }
//            }
//        }catch (Exception e){
//            System.out.println(e.getMessage());
//            return false;
//        }
//        return false;
//    }

//    @Override
//    public int updateQuoteForProductByNoteForUser(int idQuoteDetail, int quantityChange, double priceChange, String note) {
//        try {
//            TimeZone timeZone = TimeZone.getTimeZone("Asia/Ho_Chi_Minh");
//
//            // Lấy thời gian hiện tại dựa trên múi giờ của Việt Nam
//            Calendar calendar = Calendar.getInstance(timeZone);
//            Date currentTime = calendar.getTime();
//            Optional<QuoteDetailEntity> quoteDetailEntityOptional = quoteDetailRepository.findById(idQuoteDetail);
//            if (quoteDetailEntityOptional.isPresent()) {
//                QuoteDetailEntity quoteDetailEntity = quoteDetailEntityOptional.get();
//
//                // Kiểm tra nếu note không rỗng, tạo mới QuoteDetailEntity
//                if (!note.isEmpty()) {
//                    QuoteDetailEntity newQuoteDetailEntity = new QuoteDetailEntity();
//                    newQuoteDetailEntity.setProduct(quoteDetailEntity.getProduct());
//                    newQuoteDetailEntity.setQuantity(quantityChange);
//                    newQuoteDetailEntity.setPrice(quantityChange * quoteDetailEntity.getProduct().getPrice());
//                    newQuoteDetailEntity.setArea(0);
//                    newQuoteDetailEntity.setNote(note);
//                    newQuoteDetailEntity.setStatus("ACTIVE");
//                    newQuoteDetailEntity.setCreatedAt(currentTime);
//                    newQuoteDetailEntity.setUpdatedAt(currentTime);
//                    quoteDetailRepository.save(newQuoteDetailEntity);
//                    QuoteDetailEntity savedQuoteDetailEntity = quoteDetailRepository.save(quoteDetailEntity);
//                    return savedQuoteDetailEntity.getId();
//                }
//
//                // Cập nhật thông tin cho QuoteDetailEntity hiện tại
//                Optional<ProductEntity> productEntityOptional = productRepository.findById(quoteDetailEntity.getProduct().getId());
//                if (productEntityOptional.isPresent()) {
//                    quoteDetailEntity.setProduct(productEntityOptional.get());
//                    quoteDetailEntity.setQuantity(quantityChange);
//                    quoteDetailEntity.setPrice(quantityChange * productEntityOptional.get().getPrice());
//                    quoteDetailEntity.setArea(0);
//                    quoteDetailEntity.setUpdatedAt(currentTime);
//
//                    return idQuoteDetail;
//                }
//            }
//            return  0;
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
//        return 0;
//    }

    @Override
    public boolean updateQuoteForRaw(int idQuoteDetail, int idRawMaterial, double areaChange) {
        try{
            double price = 0;
            System.out.println(idQuoteDetail + " " +idRawMaterial+ " " +areaChange+ " ");
            Optional<QuoteDetailEntity> quoteDetailEntity = quoteDetailRepository.findById(idQuoteDetail);
            if(quoteDetailEntity.isPresent()) {
                Optional<RawMaterialEntity> rawMaterialEntity = rawMaterialRepository.findById(quoteDetailEntity.get().getRawMaterial().getId());
                if (rawMaterialEntity.isPresent()) {
                    price = rawMaterialEntity.get().getPricePerM2();
                    TimeZone timeZone = TimeZone.getTimeZone("Asia/Ho_Chi_Minh");

                    // Lấy thời gian hiện tại dựa trên múi giờ của Việt Nam
                    Calendar calendar = Calendar.getInstance(timeZone);
                    Date currentTime = calendar.getTime();
                    Optional<RawMaterialEntity> rawMaterialEntityChange = rawMaterialRepository.findById(idRawMaterial);
                    if (idRawMaterial != rawMaterialEntity.get().getId() && rawMaterialEntityChange.isPresent()) {
                        quoteDetailEntity.get().setRawMaterial(rawMaterialEntityChange.get());
                        price = rawMaterialEntityChange.get().getPricePerM2();
                    }


                    if (areaChange != quoteDetailEntity.get().getArea()) {
                        quoteDetailEntity.get().setArea(areaChange);
                    }

                    quoteDetailEntity.get().setQuantity(0);

                    quoteDetailEntity.get().setTotalPrice(areaChange * price);

                    quoteDetailEntity.get().setUpdatedAt(currentTime);
                    quoteDetailRepository.save(quoteDetailEntity.get());
                    return true;
                }else{
                    return false;
                }
            }else{
                return false;
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public QuoteDetailDTO findById(int id) {
        Optional<QuoteDetailEntity> quoteDetailEntity = quoteDetailRepository.findById(id);
        QuoteDetailDTO quoteDetailDTO = new QuoteDetailDTO();
        if(quoteDetailEntity.isPresent()){
            quoteDetailDTO.setId(quoteDetailEntity.get().getId());
            if(quoteDetailEntity.get().getProduct() != null){
                quoteDetailDTO.setProduct(mapProductToDTO(quoteDetailEntity.get().getProduct()));
            }
            if(quoteDetailEntity.get().getRawMaterial() != null){
                quoteDetailDTO.setRawMaterial(mapRawMaterialToDTO(quoteDetailEntity.get().getRawMaterial()));
            }
            quoteDetailDTO.setNote(quoteDetailEntity.get().getNote());
            quoteDetailDTO.setQuantity(quoteDetailEntity.get().getQuantity());
            quoteDetailDTO.setArea(quoteDetailEntity.get().getArea());
            quoteDetailDTO.setTotalPrice(quoteDetailEntity.get().getTotalPrice());
            quoteDetailDTO.setPriceChange(quoteDetailEntity.get().getPriceChange());
            quoteDetailDTO.setCreatedAt(quoteDetailEntity.get().getCreatedAt());
            quoteDetailDTO.setUpdatedAt(quoteDetailEntity.get().getUpdatedAt());
            quoteDetailDTO.setStatus(quoteDetailEntity.get().getStatus());
            return quoteDetailDTO;
        }
        return null;
    }

    @Override
    public List<QuoteDetailDTO> findAll() {
        List<QuoteDetailEntity> quoteDetailEntityList = quoteDetailRepository.findAll();
        return quoteDetailEntityList.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public ProductDTO mapProductToDTO(ProductEntity productEntity) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(productEntity.getId());
        productDTO.setName(productEntity.getName());
        productDTO.setImg(productEntity.getImage());
        productDTO.setDescription(productEntity.getDescription());
        productDTO.setHeight(productEntity.getHeight());
        productDTO.setLength(productEntity.getLength());
        productDTO.setWidth(productEntity.getWidth());
        productDTO.setPrice(productEntity.getPrice());
        productDTO.setType(productEntity.getType());
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
        // Không thêm quoteDetailEntityList vì chúng ta không muốn chuyển các mối quan hệ của RawMaterialEntity
        return rawMaterialDTO;
    }

    private QuoteDetailDTO mapToDto(QuoteDetailEntity quoteDetailEntity) {
        QuoteDetailDTO quoteDetailDTO = new QuoteDetailDTO();
        quoteDetailDTO.setId(quoteDetailEntity.getId());
        quoteDetailDTO.setQuantity(quoteDetailEntity.getQuantity());
        quoteDetailDTO.setTotalPrice(quoteDetailEntity.getTotalPrice());
        quoteDetailDTO.setPriceChange(quoteDetailEntity.getPriceChange());
        quoteDetailDTO.setArea(quoteDetailEntity.getArea());
        quoteDetailDTO.setNote(quoteDetailEntity.getNote());
        if(quoteDetailEntity.getQuote() != null){
            quoteDetailDTO.setQuoteId(quoteDetailEntity.getQuote().getId());
        }
        if(quoteDetailEntity.getProduct() != null){
            quoteDetailDTO.setProduct(mapProductToDTO(quoteDetailEntity.getProduct()));
        }
        if(quoteDetailEntity.getRawMaterial() != null){
            quoteDetailDTO.setRawMaterial(mapRawMaterialToDTO(quoteDetailEntity.getRawMaterial()));
        }
        quoteDetailDTO.setCreatedAt(quoteDetailEntity.getCreatedAt());
        quoteDetailDTO.setUpdatedAt(quoteDetailEntity.getUpdatedAt());
        quoteDetailDTO.setStatus(quoteDetailEntity.getStatus());
        return quoteDetailDTO;
    }
}
