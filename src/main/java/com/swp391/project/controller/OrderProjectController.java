//package com.swp391.project.controller;
//
//import com.swp391.project.dto.OrderProjectDTO;
//import com.swp391.project.payload.request.OrderProjectRequest;
//import com.swp391.project.payload.response.BaseResponse;
//import com.swp391.project.service.impl.OrderProjectImp;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.parameters.P;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/order/project")
//@CrossOrigin(origins = {"http://localhost:8082", "https://furniture-quote.azurewebsites.net",
//        "https://eclectic-belekoy-79d097.netlify.app/", "http://localhost:3000"}, allowCredentials = "true")
//public class OrderProjectController {
//
//    @Autowired
//    private OrderProjectImp orderProjectImp;
//
//    @GetMapping("/getAllProjectByUserId")
//    public ResponseEntity<?> getAllProjectByUserId(@RequestParam int idUser){
//        BaseResponse baseResponse = new BaseResponse();
//        List<OrderProjectDTO> orderProjectDTOS = orderProjectImp.getAllProjectsByUserId(idUser);
//        if(!orderProjectDTOS.isEmpty()){
//            baseResponse.setMesssage("SucessFull");
//            baseResponse.setStatusCode(200);
//            baseResponse.setData(orderProjectDTOS);
//            return new ResponseEntity<>(baseResponse, HttpStatus.OK);
//        }
//        baseResponse.setMesssage("Not found");
//        baseResponse.setStatusCode(200);
//        baseResponse.setData(orderProjectDTOS);
//        return new ResponseEntity<>(baseResponse, HttpStatus.NOT_FOUND);
//    }
//
//    @PostMapping("/createOrderProject")
//    public ResponseEntity<?> createOrderProject(@RequestBody OrderProjectRequest orderProjectRequest){
//        BaseResponse baseResponse = new BaseResponse();
//        boolean check = orderProjectImp.create(orderProjectRequest);
//        if(check){
//            baseResponse.setStatusCode(201);
//            baseResponse.setMesssage("Create Successfull");
//            baseResponse.setData("True");
//            return new ResponseEntity<>(baseResponse, HttpStatus.OK);
//        }
//        baseResponse.setStatusCode(200);
//        baseResponse.setMesssage("Create Failed");
//        baseResponse.setData("False");
//        return new ResponseEntity<>(baseResponse,HttpStatus.NOT_FOUND);
//    }
//
//    @PutMapping("/updateOrderProject")
//    public ResponseEntity<?> updateOrderProject(@RequestParam int orderProjectId,String status){
//        BaseResponse baseResponse = new BaseResponse();
//        boolean check = orderProjectImp.update(orderProjectId,status);
//        if(check){
//            baseResponse.setStatusCode(200);
//            baseResponse.setMesssage("Update Successfull");
//            baseResponse.setData("True");
//            return new ResponseEntity<>(baseResponse, HttpStatus.OK);
//        }
//        baseResponse.setStatusCode(400);
//        baseResponse.setMesssage("Update Failed");
//        baseResponse.setData("False");
//        return new ResponseEntity<>(baseResponse,HttpStatus.NOT_FOUND);
//    }
//}
