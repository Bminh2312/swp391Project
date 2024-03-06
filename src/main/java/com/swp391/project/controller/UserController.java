package com.swp391.project.controller;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.Http;
import com.swp391.project.dto.OrderProjectDTO;
import com.swp391.project.dto.UserDetailDTO;
import com.swp391.project.dto.UserWithProjectsDTO;
import com.swp391.project.entity.UserEntity;
import com.swp391.project.payload.response.BaseResponse;
import com.swp391.project.service.impl.OrderProjectImp;
import com.swp391.project.service.impl.UserDetailServiceImp;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @GetMapping("/getAllProjectByUserId")
    public ResponseEntity<?> getAllProjectByUserId(@RequestParam int idUser){
        BaseResponse baseResponse = new BaseResponse();
        UserWithProjectsDTO userWithProjects = userDetailServiceImp.getUserWithProjects(idUser);
        if(userWithProjects != null){
            if(userWithProjects.getProjects().isEmpty()){
                baseResponse.setMesssage("User do not have any project");
                baseResponse.setStatusCode(200);
                baseResponse.setData(userWithProjects);
                return new ResponseEntity<>(baseResponse, HttpStatus.OK);
            }
            baseResponse.setMesssage("SucessFull");
            baseResponse.setStatusCode(200);
            baseResponse.setData(userWithProjects);
            return new ResponseEntity<>(baseResponse, HttpStatus.OK);
        }
        baseResponse.setMesssage("Not found");
        baseResponse.setStatusCode(200);
        baseResponse.setData("Null");
        return new ResponseEntity<>(baseResponse, HttpStatus.NOT_FOUND);
    }

    @PutMapping(value = "/updateUser",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateUser (@RequestParam int userId,
                                         @RequestParam("fullName") String fullName,
                                         @RequestParam("phone") String phone,
                                         @RequestParam("address") String address,
                                         @RequestParam(name = "fileImg", required = false) MultipartFile avt) {
        BaseResponse baseResponse = new BaseResponse();
        boolean check = userDetailServiceImp.updateUser(userId, fullName, phone, address, avt);
        if(check){
            baseResponse.setMesssage("Update successful");
            baseResponse.setStatusCode(200);
            baseResponse.setData("True");
            return new ResponseEntity<>(baseResponse, HttpStatus.OK);
        }
        baseResponse.setMesssage("Update fail");
        baseResponse.setStatusCode(200);
        baseResponse.setData("False");
        return new ResponseEntity<>(baseResponse, HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(value = "/deleteUser")
    public ResponseEntity<?> deleteUser(@RequestParam int userId){
        BaseResponse baseResponse = new BaseResponse();
        boolean check = userDetailServiceImp.setStatusUser(userId,"INACTIVE");
        if(check){
            baseResponse.setMesssage("Delete successful");
            baseResponse.setStatusCode(200);
            baseResponse.setData("True");
            return new ResponseEntity<>(baseResponse, HttpStatus.OK);
        }
        baseResponse.setMesssage("Delete fail");
        baseResponse.setStatusCode(200);
        baseResponse.setData("False");
        return new ResponseEntity<>(baseResponse, HttpStatus.BAD_REQUEST);
    }

    @PutMapping(value = "/updateStatusUser")
    public ResponseEntity<?> updateStatusUser(@RequestParam int userId,
                                              @RequestParam @Schema(description = "Status", allowableValues = {"ACTIVE", "INACTIVE"}) String status){
        BaseResponse baseResponse = new BaseResponse();
        boolean check = userDetailServiceImp.setStatusUser(userId, status);
        if(check){
            baseResponse.setMesssage("Delete successful");
            baseResponse.setStatusCode(200);
            baseResponse.setData("True");
            return new ResponseEntity<>(baseResponse, HttpStatus.OK);
        }
        baseResponse.setMesssage("Set status fail");
        baseResponse.setStatusCode(200);
        baseResponse.setData("False");
        return new ResponseEntity<>(baseResponse, HttpStatus.BAD_REQUEST);
    }
}
