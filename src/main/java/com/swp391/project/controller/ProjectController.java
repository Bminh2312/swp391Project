package com.swp391.project.controller;


import com.swp391.project.dto.DesignStyleDTO;
import com.swp391.project.dto.ProjectDTO;
import com.swp391.project.payload.request.DesignStypeRequest;
import com.swp391.project.payload.request.ProjectRequest;
import com.swp391.project.payload.response.BaseResponse;
import com.swp391.project.service.impl.ProjectImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/project")
public class ProjectController {

    @Autowired
    private ProjectImp projectImp;

    @PostMapping(value = "/createProject")
    public ResponseEntity<?> create( @RequestBody ProjectRequest projectRequest){
        BaseResponse baseResponse = new BaseResponse();
        boolean check = projectImp.create(projectRequest);
        if(check){
            baseResponse.setStatusCode(200);
            baseResponse.setMesssage("Create Successfull");
            baseResponse.setData("True");
            return new ResponseEntity<>(baseResponse, HttpStatus.OK);
        }
        baseResponse.setStatusCode(201);
        baseResponse.setMesssage("Create Failed");
        baseResponse.setData("False");
        return new ResponseEntity<>(baseResponse,HttpStatus.NOT_FOUND);

    }

    @PostMapping(value = "/updateProject")
    public ResponseEntity<?> update(@RequestBody ProjectRequest projectRequest, @RequestParam int projectId){
        boolean check = projectImp.update(projectRequest,projectId);
        BaseResponse baseResponse = new BaseResponse();
        if(check){
            baseResponse.setStatusCode(200);
            baseResponse.setMesssage("Update Successfull");
            baseResponse.setData("True");
            return new ResponseEntity<>(baseResponse,HttpStatus.OK);
        }
        baseResponse.setStatusCode(201);
        baseResponse.setMesssage("Update Failed");
        baseResponse.setData("False");
        return new ResponseEntity<>(baseResponse,HttpStatus.NOT_FOUND);

    }

    @GetMapping("/getDesignById")
    public ResponseEntity<?> getAllDesign(@RequestParam int id){
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setMesssage("SucessFull");
        baseResponse.setStatusCode(200);
        ProjectDTO projectDTO = projectImp.findById(id);
        if(projectDTO == null){
            baseResponse.setMesssage("Not Found");
            baseResponse.setStatusCode(200);
            baseResponse.setData(null);
            return new ResponseEntity<>(baseResponse, HttpStatus.NOT_FOUND);
        }else{
            baseResponse.setData(projectDTO);
            return new ResponseEntity<>(baseResponse, HttpStatus.OK);
        }


    }


}
