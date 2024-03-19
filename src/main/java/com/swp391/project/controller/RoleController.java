package com.swp391.project.controller;

import com.swp391.project.dto.RoleDTO;
import com.swp391.project.dto.TypeProjectDTO;
import com.swp391.project.payload.response.BaseResponse;
import com.swp391.project.service.impl.RoleServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/role")
@CrossOrigin(origins = {"http://localhost:8082", "https://furniture-quote.azurewebsites.net",
        "https://eclectic-belekoy-79d097.netlify.app/", "http://localhost:3000"}, allowCredentials = "true")
public class RoleController {
    @Autowired
    private RoleServiceImp roleServiceImp;

    @GetMapping("/getAllRole")
    public ResponseEntity<?> getAllDesign(){
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setMesssage("SucessFull");
        baseResponse.setStatusCode(200);
        List<RoleDTO> roleDTOS = roleServiceImp.findAllType();
        if(roleDTOS.isEmpty()){
            baseResponse.setData(null);
        }else{
            baseResponse.setData(roleDTOS);
        }

        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @GetMapping("/getRoleById")
    public ResponseEntity<?> getAllDesign(@RequestParam int id){
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setMesssage("SucessFull");
        baseResponse.setStatusCode(200);
        RoleDTO roleDTO = roleServiceImp.findById(id);
        if(roleDTO == null){
            baseResponse.setMesssage("Not Found");
            baseResponse.setStatusCode(200);
            baseResponse.setData(null);
            return new ResponseEntity<>(baseResponse, HttpStatus.NOT_FOUND);
        }else{
            baseResponse.setData(roleDTO);
            return new ResponseEntity<>(baseResponse, HttpStatus.OK);
        }


    }
}
