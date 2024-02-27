package com.swp391.project.controller;

import com.swp391.project.payload.response.BaseResponse;
import com.swp391.project.service.impl.DesignStyleImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = {"http://localhost:8082", "https://furniture-quote.azurewebsites.net",
        "https://eclectic-belekoy-79d097.netlify.app/", "http://localhost:3000"}, allowCredentials = "true")
@RequestMapping("/designStyle")
public class DesignStyleController {

    @Autowired
    private DesignStyleImp designStyleImp;

    @GetMapping("/getAllDesign")
    public ResponseEntity<?> getAllDesign(){
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setMesssage("SucessFull");
        baseResponse.setStatusCode(200);
        if(designStyleImp.findAllDesign().isEmpty()){
            baseResponse.setData(null);
        }else{
            baseResponse.setData(designStyleImp.findAllDesign());
        }

        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }
}
