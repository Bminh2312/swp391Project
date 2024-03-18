package com.swp391.project.controller;

import com.swp391.project.dto.DesignStyleDTO;
import com.swp391.project.dto.TypeProjectDTO;
import com.swp391.project.payload.response.BaseResponse;
import com.swp391.project.service.impl.TypeProjectServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/type")
@CrossOrigin(origins = {"http://localhost:8082", "https://furniture-quote.azurewebsites.net",
        "https://eclectic-belekoy-79d097.netlify.app/", "http://localhost:3000"}, allowCredentials = "true")
public class TypeProjectController {

    @Autowired
    private TypeProjectServiceImp typeProjectServiceImp;

    @GetMapping("/getAllType")
    public ResponseEntity<?> getAllDesign(){
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setMesssage("SucessFull");
        baseResponse.setStatusCode(200);
        List<TypeProjectDTO> typeProjectDTOS = typeProjectServiceImp.findAllType();
        if(typeProjectDTOS.isEmpty()){
            baseResponse.setData(null);
        }else{
            baseResponse.setData(typeProjectDTOS);
        }

        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @GetMapping("/getTypeById")
    public ResponseEntity<?> getAllDesign(@RequestParam int id){
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setMesssage("SucessFull");
        baseResponse.setStatusCode(200);
        TypeProjectDTO typeProjectDTO = typeProjectServiceImp.findById(id);
        if(typeProjectDTO == null){
            baseResponse.setMesssage("Not Found");
            baseResponse.setStatusCode(200);
            baseResponse.setData(null);
            return new ResponseEntity<>(baseResponse, HttpStatus.NOT_FOUND);
        }else{
            baseResponse.setData(typeProjectDTO);
            return new ResponseEntity<>(baseResponse, HttpStatus.OK);
        }


    }
}
