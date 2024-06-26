package com.swp391.project.controller;

import com.swp391.project.dto.DesignStyleDTO;
import com.swp391.project.payload.request.DesignStypeRequest;
import com.swp391.project.payload.response.BaseResponse;
import com.swp391.project.service.impl.DesignStyleServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:8082", "https://furniture-quote.azurewebsites.net",
        "https://eclectic-belekoy-79d097.netlify.app/", "http://localhost:3000"}, allowCredentials = "true")
@RequestMapping("/designStyle")
public class DesignStyleController {

    @Autowired
    private DesignStyleServiceImp designStyleImp;

    @GetMapping("/getAllDesign")
    public ResponseEntity<?> getAllDesign(){
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setMesssage("SucessFull");
        baseResponse.setStatusCode(200);
        List<DesignStyleDTO> designStyleDTOList = designStyleImp.findAllDesign();
        if(designStyleDTOList.isEmpty()){
            baseResponse.setData(null);
        }else{
            baseResponse.setData(designStyleDTOList);
        }

        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @PostMapping(value = "/createDesign", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> create(@RequestPart(name = "file", required = true) MultipartFile file, @RequestParam("name") String name,
                                    @RequestParam("description") String description,
                                    @RequestParam("price") double price) {
        try {
            // Xử lý logic của bạn ở đây với file và designStypeRequest
            // Ví dụ:
            System.out.println(name);
            System.out.println(description);
            boolean check = designStyleImp.create(name,description, price, file);

            BaseResponse baseResponse = new BaseResponse();
            if (check) {
                baseResponse.setStatusCode(201);
                baseResponse.setMesssage("Create Successful");
                baseResponse.setData("True");
                return new ResponseEntity<>(baseResponse, HttpStatus.OK);
            }
            baseResponse.setStatusCode(200);
            baseResponse.setMesssage("Create Failed");
            baseResponse.setData("False");
            return new ResponseEntity<>(baseResponse, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error processing request.");
        }
    }

    @PutMapping(value = "/updateDesign",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> update(@RequestPart(name = "file", required = false) MultipartFile file, @RequestParam("name") String name,
                                    @RequestParam(name = "price", required = false) double price,
                                    @RequestParam(name = "description", required = false) String description,
                                    @RequestParam(name = "description", required = false) String status,
                                    @RequestParam(name = "designId") int designId){
        boolean check = designStyleImp.update(name,description,price,file,status,designId);
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

    @GetMapping("/getDesignById")
    public ResponseEntity<?> getAllDesign(@RequestParam int id){
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setMesssage("SucessFull");
        baseResponse.setStatusCode(200);
        DesignStyleDTO designStyleDTO = designStyleImp.findById(id);
        if(designStyleDTO == null){
            baseResponse.setMesssage("Not Found");
            baseResponse.setStatusCode(200);
            baseResponse.setData(null);
            return new ResponseEntity<>(baseResponse, HttpStatus.NOT_FOUND);
        }else{
            baseResponse.setData(designStyleDTO);
            return new ResponseEntity<>(baseResponse, HttpStatus.OK);
        }


    }
}
