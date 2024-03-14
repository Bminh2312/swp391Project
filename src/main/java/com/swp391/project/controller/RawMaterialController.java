package com.swp391.project.controller;

import com.swp391.project.dto.ProductDTO;
import com.swp391.project.dto.RawMaterialDTO;
import com.swp391.project.payload.response.BaseResponse;
import com.swp391.project.service.impl.RawMaterialServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/rawMaterial")
@CrossOrigin(origins = {"http://localhost:8082", "https://furniture-quote.azurewebsites.net",
        "https://eclectic-belekoy-79d097.netlify.app/", "http://localhost:3000"}, allowCredentials = "true")
public class RawMaterialController {

    @Autowired
    private RawMaterialServiceImp rawMaterialServiceImp;

    @GetMapping("/getAllRawMaterial")
    public ResponseEntity<?> getAllProduct(Pageable pageable){
        BaseResponse baseResponse = new BaseResponse();
        Page<RawMaterialDTO> productDTOS = rawMaterialServiceImp.findAll(pageable);
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
    public ResponseEntity<?> create(@RequestPart(name = "fileImg", required = true) MultipartFile fileImg, @RequestParam(name = "name") String name,
                                    @RequestParam(name = "description") String description,
                                    @RequestParam(name = "type") String type,
                                    @RequestParam(name = "pricePerM2") double pricePerM2){

        boolean check = rawMaterialServiceImp.create(name,description,type,pricePerM2,fileImg);
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
    public ResponseEntity<?> update(@RequestPart(name = "fileImg", required = false) MultipartFile fileImg, @RequestParam int rawMaterialId, @RequestParam(name = "name", required = false) String name,
                                    @RequestParam(name = "description", required = false) String description,
                                    @RequestParam(name = "type", required = false) String type,
                                    @RequestParam(name = "pricePerM2", required = false) double pricePerM2){
        System.out.println(name);
        boolean check = rawMaterialServiceImp.update(name,description,type,pricePerM2,fileImg,rawMaterialId);
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
