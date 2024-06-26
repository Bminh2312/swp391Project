package com.swp391.project.controller;

import com.swp391.project.dto.ProductDTO;
import com.swp391.project.dto.UserDetailDTO;
import com.swp391.project.payload.request.DesignStypeRequest;
import com.swp391.project.payload.request.ProductRequest;
import com.swp391.project.payload.request.ProjectRequest;
import com.swp391.project.payload.response.BaseResponse;
import com.swp391.project.service.impl.ProductServiceImp;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/product")
@CrossOrigin(origins = {"http://localhost:8082", "https://furniture-quote.azurewebsites.net",
        "https://eclectic-belekoy-79d097.netlify.app/", "http://localhost:3000"}, allowCredentials = "true"
        )
public class ProductController {

    @Autowired
    private ProductServiceImp productServiceImp;

    @GetMapping("/getAllProduct")
    public ResponseEntity<?> getAllProduct(Pageable pageable){
        BaseResponse baseResponse = new BaseResponse();
        Page<ProductDTO> productDTOS = productServiceImp.findAll(pageable);
        if(!productDTOS.isEmpty()){
            baseResponse.setData(productDTOS);
            baseResponse.setTotalPages(productDTOS.getTotalPages());
            baseResponse.setMesssage("Successfull");
            baseResponse.setStatusCode(200);
            return  new ResponseEntity<>(baseResponse, HttpStatus.OK);
        }else{
            baseResponse.setData(null);
            baseResponse.setMesssage("Not Found");
            baseResponse.setStatusCode(400);
            return  new ResponseEntity<>(baseResponse, HttpStatus.OK);
        }
    }

    @GetMapping("/getAllPageProductByType")
    public ResponseEntity<?> getAllProduct(Pageable pageable, @RequestParam(name = "type", required = false) String type){
        BaseResponse baseResponse = new BaseResponse();
        Page<ProductDTO> productDTOS = productServiceImp.findAllByType(type,pageable);
        if(!productDTOS.isEmpty()){
            baseResponse.setData(productDTOS);
            baseResponse.setTotalPages(productDTOS.getTotalPages());
            baseResponse.setMesssage("Successfull");
            baseResponse.setStatusCode(200);
            return  new ResponseEntity<>(baseResponse, HttpStatus.OK);
        }else{
            baseResponse.setData(null);
            baseResponse.setMesssage("Not Found");
            baseResponse.setStatusCode(400);
            return  new ResponseEntity<>(baseResponse, HttpStatus.OK);
        }
    }

    @GetMapping("/getAllType")
    public ResponseEntity<?> getAllType(){
        BaseResponse baseResponse = new BaseResponse();
        List<String> types = productServiceImp.findAllType();
        if(!types.isEmpty()){
            baseResponse.setData(types);
            baseResponse.setMesssage("Successfull");
            baseResponse.setStatusCode(200);
            return  new ResponseEntity<>(baseResponse, HttpStatus.OK);
        }else{
            baseResponse.setData(null);
            baseResponse.setMesssage("Not Found");
            baseResponse.setStatusCode(400);
            return  new ResponseEntity<>(baseResponse, HttpStatus.OK);
        }
    }

//    @GetMapping("/getAllProductByType")
//    public ResponseEntity<?> getAllProductByType(@RequestParam String type){
//        BaseResponse baseResponse = new BaseResponse();
//        List<ProductDTO> productDTOS = productServiceImp.findByType(type);
//        if(!productDTOS.isEmpty()){
//            baseResponse.setData(productDTOS);
//            baseResponse.setMesssage("Successfull");
//            baseResponse.setStatusCode(200);
//            return  new ResponseEntity<>(baseResponse, HttpStatus.OK);
//        }else{
//            baseResponse.setData(null);
//            baseResponse.setMesssage("Not Found");
//            baseResponse.setStatusCode(400);
//            return  new ResponseEntity<>(baseResponse, HttpStatus.OK);
//        }
//    }

    @PostMapping(value = "/createProduct",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> create(@RequestPart(name = "fileImg", required = true) MultipartFile fileImg, @RequestParam(name = "name", required = false) String name,
                                    @RequestParam(name = "description", required = false) String description,
                                    @RequestParam(name = "type", required = false) String type,
                                    @RequestParam(name = "height", required = false) double height,
                                    @RequestParam(name = "length", required = false) double length,
                                    @RequestParam(name = "width", required = false) double width,
                                    @RequestParam(name = "price", required = false) double price){


        boolean check = productServiceImp.create(name,description,type,height,length,width,price,fileImg);
        BaseResponse baseResponse = new BaseResponse();
        if(check){
            baseResponse.setStatusCode(201);
            baseResponse.setMesssage("Create Successfull");
            baseResponse.setData("True");
            return new ResponseEntity<>(baseResponse, HttpStatus.OK);
        }
        baseResponse.setStatusCode(200);
        baseResponse.setMesssage("Create Failed");
        baseResponse.setData("False");
        return new ResponseEntity<>(baseResponse,HttpStatus.NOT_FOUND);

    }


    @PutMapping(value = "/updateProduct",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> update(@RequestPart(name = "fileImg", required = false) MultipartFile fileImg, @RequestParam int productId, @RequestParam(name = "name", required = false) String name,
                                    @RequestParam(name = "description", required = false) String description,
                                    @RequestParam(name = "type", required = false) String type,
                                    @RequestParam(name = "height", required = false, defaultValue = "0") double height,
                                    @RequestParam(name = "length", required = false,  defaultValue = "0") double length,
                                    @RequestParam(name = "width", required = false,  defaultValue = "0") double width,
                                    @RequestParam(name = "price", required = false,  defaultValue = "0") double price){
        System.out.println(name);
        boolean check = productServiceImp.update(name,description,type,height,length,width,price,fileImg,productId);
        BaseResponse baseResponse = new BaseResponse();
        if(check){
            baseResponse.setStatusCode(200);
            baseResponse.setMesssage("Update Successfull");
            baseResponse.setData("True");
            return new ResponseEntity<>(baseResponse,HttpStatus.OK);
        }
        baseResponse.setStatusCode(400);
        baseResponse.setMesssage("Update Failed");
        baseResponse.setData("False");
        return new ResponseEntity<>(baseResponse,HttpStatus.NOT_FOUND);

    }

    @DeleteMapping(value = "/deleteProduct")
    public ResponseEntity<?> delete(@RequestParam("id") int id, @RequestParam @Schema(description = "Status", allowableValues = {"ACTIVE", "INACTIVE"}) String status){
        boolean check = productServiceImp.delete(id,status);
        BaseResponse baseResponse = new BaseResponse();
        if(check){
            baseResponse.setStatusCode(201);
            baseResponse.setMesssage("Delete Successfull");
            baseResponse.setData("True");
            return new ResponseEntity<>(baseResponse, HttpStatus.OK);
        }
        baseResponse.setStatusCode(200);
        baseResponse.setMesssage("Delete Failed");
        baseResponse.setData("False");
        return new ResponseEntity<>(baseResponse,HttpStatus.NOT_FOUND);

    }
}
