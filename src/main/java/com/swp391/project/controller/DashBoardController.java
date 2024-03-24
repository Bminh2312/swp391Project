package com.swp391.project.controller;

import com.swp391.project.payload.response.BaseResponse;
import com.swp391.project.service.ProductService;
import com.swp391.project.service.impl.ProductServiceImp;
import com.swp391.project.service.impl.ProjectServiceImp;
import com.swp391.project.service.impl.RawMaterialServiceImp;
import com.swp391.project.service.impl.UserDetailServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = {"http://localhost:8082", "https://furniture-quote.azurewebsites.net",
        "https://eclectic-belekoy-79d097.netlify.app/", "http://localhost:3000"}, allowCredentials = "true")
@RequestMapping("/dashboard")
public class DashBoardController {
    @Autowired
    private ProjectServiceImp projectServiceImp;

    @Autowired
    private ProductServiceImp productServiceImp;

    @Autowired
    private RawMaterialServiceImp rawMaterialServiceImp;

    @Autowired
    private UserDetailServiceImp userDetailServiceImp;

    @GetMapping("/getTotalProjectByStatus")
    public ResponseEntity<?> getTotalProjectByStatus(@RequestParam String status){
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setMesssage("SucessFull");
        baseResponse.setStatusCode(200);
        long count = projectServiceImp.findTotalProjectByStatus(status);
        if(count == 0){
            baseResponse.setMesssage("Not Found");
            baseResponse.setStatusCode(400);
            baseResponse.setData(count);
            return new ResponseEntity<>(baseResponse, HttpStatus.NOT_FOUND);
        }else{
            baseResponse.setData(count);
            return new ResponseEntity<>(baseResponse, HttpStatus.OK);
        }
    }

    @GetMapping("/getTotalProductByStatus")
    public ResponseEntity<?> getTotalProductByStatus(@RequestParam String status){
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setMesssage("SucessFull");
        baseResponse.setStatusCode(200);
        long count = productServiceImp.countByStatus(status);
        if(count == 0){
            baseResponse.setMesssage("Not Found");
            baseResponse.setStatusCode(400);
            baseResponse.setData(count);
            return new ResponseEntity<>(baseResponse, HttpStatus.NOT_FOUND);
        }else{
            baseResponse.setData(count);
            return new ResponseEntity<>(baseResponse, HttpStatus.OK);
        }
    }

    @GetMapping("/getTotalRawByStatus")
    public ResponseEntity<?> getTotalRawByStatus(@RequestParam String status){
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setMesssage("SucessFull");
        baseResponse.setStatusCode(200);
        long count = rawMaterialServiceImp.countByStatus(status);
        if(count == 0){
            baseResponse.setMesssage("Not Found");
            baseResponse.setStatusCode(400);
            baseResponse.setData(count);
            return new ResponseEntity<>(baseResponse, HttpStatus.NOT_FOUND);
        }else{
            baseResponse.setData(count);
            return new ResponseEntity<>(baseResponse, HttpStatus.OK);
        }
    }

    @GetMapping("/getTotalUserByStatus")
    public ResponseEntity<?> getTotalUserByStatus(@RequestParam String status){
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setMesssage("SucessFull");
        baseResponse.setStatusCode(200);
        long count = userDetailServiceImp.countByStatus(status);
        if(count == 0){
            baseResponse.setMesssage("Not Found");
            baseResponse.setStatusCode(400);
            baseResponse.setData(count);
            return new ResponseEntity<>(baseResponse, HttpStatus.NOT_FOUND);
        }else{
            baseResponse.setData(count);
            return new ResponseEntity<>(baseResponse, HttpStatus.OK);
        }
    }

    @GetMapping("/getTotalUserByStatusAndRoleId")
    public ResponseEntity<?> getTotalUserByStatusAndRoleId(@RequestParam String status, @RequestParam int roleId){
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setMesssage("SucessFull");
        baseResponse.setStatusCode(200);
        long count = userDetailServiceImp.countByStatusAndRole_Id(status,roleId);
        if(count == 0){
            baseResponse.setMesssage("Not Found");
            baseResponse.setStatusCode(400);
            baseResponse.setData(count);
            return new ResponseEntity<>(baseResponse, HttpStatus.OK);
        }else{
            baseResponse.setData(count);
            return new ResponseEntity<>(baseResponse, HttpStatus.OK);
        }
    }




}
