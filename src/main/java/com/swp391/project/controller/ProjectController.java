package com.swp391.project.controller;


import com.swp391.project.dto.ProjectDTO;
import com.swp391.project.payload.request.ProjectRequest;
import com.swp391.project.payload.response.BaseResponse;
import com.swp391.project.service.impl.ProjectServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/project")
@CrossOrigin(origins = {"http://localhost:8082", "https://furniture-quote.azurewebsites.net",
        "https://eclectic-belekoy-79d097.netlify.app/", "http://localhost:3000"}, allowCredentials = "true")
public class ProjectController {

    @Autowired
    private ProjectServiceImp projectImp;

    @PostMapping(value = "/createProject")
    public ResponseEntity<?> create( @RequestBody ProjectRequest projectRequest){
        BaseResponse baseResponse = new BaseResponse();
        boolean check = projectImp.create(projectRequest);
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

    @PutMapping(value = "/updateProject")
    public ResponseEntity<?> update(@RequestBody ProjectRequest projectRequest, @RequestParam int projectId){
        boolean check = projectImp.update(projectRequest,projectId);
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

    @GetMapping("/getProjectById")
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
