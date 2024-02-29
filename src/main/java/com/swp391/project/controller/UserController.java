package com.swp391.project.controller;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.swp391.project.dto.UserDetailDTO;
import com.swp391.project.payload.response.BaseResponse;
import com.swp391.project.service.impl.UserDetailServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:8082", "https://furniture-quote.azurewebsites.net",
        "https://eclectic-belekoy-79d097.netlify.app/", "http://localhost:3000"}, allowCredentials = "true")
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserDetailServiceImp userDetailServiceImp;

    @GetMapping("/getUserByEmail")
    public ResponseEntity<?> getUserByEmail(@RequestParam String email){
        BaseResponse baseResponse = new BaseResponse();
        UserDetailDTO userDTO = userDetailServiceImp.findByEmail(email);
        if(userDTO!= null){
            baseResponse.setData(userDTO);
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

    @GetMapping("/getAllUser")
    public ResponseEntity<?> getAllUser(Pageable pageable){
        BaseResponse baseResponse = new BaseResponse();
        Page<UserDetailDTO> userDTOS = userDetailServiceImp.findAll(pageable);
        if(!userDTOS.isEmpty()){
            baseResponse.setData(userDTOS);
            baseResponse.setTotalPages(userDTOS.getTotalPages());
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


}
