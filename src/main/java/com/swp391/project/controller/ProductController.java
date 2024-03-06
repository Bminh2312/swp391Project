package com.swp391.project.controller;

import com.swp391.project.dto.ProductDTO;
import com.swp391.project.dto.UserDetailDTO;
import com.swp391.project.payload.request.DesignStypeRequest;
import com.swp391.project.payload.request.ProductRequest;
import com.swp391.project.payload.response.BaseResponse;
import com.swp391.project.service.impl.ProductServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/product")
@CrossOrigin(origins = {"http://localhost:8082", "https://furniture-quote.azurewebsites.net",
        "https://eclectic-belekoy-79d097.netlify.app/", "http://localhost:3000"}, allowCredentials = "true", allowedHeaders = "*"
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

    @PostMapping(value = "/createProduct",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> create(@RequestPart(name = "fileImg", required = true) MultipartFile fileImg, @RequestParam("name") String name,
                                    @RequestParam("description") String description,
                                    @RequestParam("type") String type,
                                    @RequestParam("height") double height,
                                    @RequestParam("length") double length,
                                    @RequestParam("width") double width,
                                    @RequestParam("pricePerM2") double pricePerM2){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Access-Control-Allow-Origin", "*");

        boolean check = productServiceImp.create(name,description,type,height,length,width,pricePerM2,fileImg);
        BaseResponse baseResponse = new BaseResponse();
        if(check){
            baseResponse.setStatusCode(201);
            baseResponse.setMesssage("Create Successfull");
            baseResponse.setData("True");
            return new ResponseEntity<>(baseResponse,HttpStatus.OK);
        }
        baseResponse.setStatusCode(200);
        baseResponse.setMesssage("Create Failed");
        baseResponse.setData("False");
        return new ResponseEntity<>(baseResponse,HttpStatus.NOT_FOUND);

    }

    @PutMapping(value = "/updateProduct",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> update(@RequestPart(name = "fileImg", required = false) MultipartFile fileImg, @RequestParam int productId, @RequestParam("name") String name,
                                    @RequestParam("description") String description,
                                    @RequestParam("type") String type,
                                    @RequestParam("height") double height,
                                    @RequestParam("length") double length,
                                    @RequestParam("width") double width,
                                    @RequestParam("pricePerM2") double pricePerM2){
        System.out.println(name);
        boolean check = productServiceImp.update(name,description,type,height,length,width,pricePerM2,fileImg,productId);
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
}
