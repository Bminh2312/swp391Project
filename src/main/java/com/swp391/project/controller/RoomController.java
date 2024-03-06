package com.swp391.project.controller;


import com.swp391.project.dto.ProductDTO;
import com.swp391.project.dto.RoomDTO;
import com.swp391.project.payload.response.BaseResponse;
import com.swp391.project.service.impl.RoomServiceImp;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/room")
@CrossOrigin(origins = {"http://localhost:8082", "https://furniture-quote.azurewebsites.net",
        "https://eclectic-belekoy-79d097.netlify.app/", "http://localhost:3000"}, allowCredentials = "true")
public class RoomController {

    @Autowired
    private RoomServiceImp roomServiceImp;

    @GetMapping("/getAllRoom")
    public ResponseEntity<?> getAllRoom(){
        BaseResponse baseResponse = new BaseResponse();
        List<RoomDTO> roomDTOS = roomServiceImp.findAll();
        if(!roomDTOS.isEmpty()){
            baseResponse.setData(roomDTOS);
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

    @PostMapping(value = "/createRoom")
    public ResponseEntity<?> create(@RequestParam("name") String name){
        boolean check = roomServiceImp.create(name);
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

    @PutMapping(value = "/updateRoom")
    public ResponseEntity<?> update(@RequestParam("id") int id, @RequestParam("name") String name){
        boolean check = roomServiceImp.update(id,name);
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

    @DeleteMapping(value = "/deleteRoom")
    public ResponseEntity<?> delete(@RequestParam("id") int id, @RequestParam @Schema(description = "Status", allowableValues = {"ACTIVE", "INACTIVE"}) String status){
        boolean check = roomServiceImp.delete(id,status);
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

}
