package com.swp391.project.controller;


import com.swp391.project.dto.ProductDTO;
import com.swp391.project.dto.ProjectDTO;
import com.swp391.project.dto.ProjectWithAllQuoteDTO;
import com.swp391.project.dto.ProjectWithUserDTO;
import com.swp391.project.payload.request.ProjectRequest;
import com.swp391.project.payload.request.ProjectSampleRequest;
import com.swp391.project.payload.response.BaseResponse;
import com.swp391.project.service.impl.ProjectServiceImp;
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
@RequestMapping("/project")
@CrossOrigin(origins = {"http://localhost:8082", "https://furniture-quote.azurewebsites.net",
        "https://eclectic-belekoy-79d097.netlify.app/", "http://localhost:3000"}, allowCredentials = "true")
public class ProjectController {

    @Autowired
    private ProjectServiceImp projectImp;


    @GetMapping("/getAllPageProjectByStatusAndUserId")
    public ResponseEntity<?> getAllPageProductByStatusAndUserId(Pageable pageable, @RequestParam(name = "status", required = false) String status, int userId){
        BaseResponse baseResponse = new BaseResponse();
        Page<ProjectDTO> projectDTOS = projectImp.findAllByStatusAndUserId(userId,status,pageable);
        if(projectDTOS.isEmpty()){
            baseResponse.setData(null);
            baseResponse.setMesssage("Not Found");
            baseResponse.setStatusCode(400);

        }else{
            baseResponse.setData(projectDTOS);
            baseResponse.setTotalPages(projectDTOS.getTotalPages());
            baseResponse.setMesssage("Successfully");
            baseResponse.setStatusCode(200);
        }
        return  new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

//    @GetMapping("/getAllPageProjectByStatus")
//    public ResponseEntity<?> getAllPageProductByStatusAndUserId(Pageable pageable, @RequestParam @Schema(description = "Status", allowableValues = {"NEW", "QUOTING","REJECTED"}) String status){
//        BaseResponse baseResponse = new BaseResponse();
//        Page<ProjectWithUserDTO> projectDTOS = projectImp.findAllByStatus(status,pageable);
//        if(!projectDTOS.isEmpty()){
//            baseResponse.setData(projectDTOS);
//            baseResponse.setTotalPages(projectDTOS.getTotalPages());
//            baseResponse.setMesssage("Successfull");
//            baseResponse.setStatusCode(200);
//            return  new ResponseEntity<>(baseResponse, HttpStatus.OK);
//        }else{
//            baseResponse.setData(null);
//            baseResponse.setMesssage("Not Found");
//            baseResponse.setStatusCode(400);
//            return  new ResponseEntity<>(baseResponse, HttpStatus.OK);
//        }
//    }

    @GetMapping("/getAllPageProjectByStatusOrDesignStyleOrType")
    public ResponseEntity<?> getAllPageProjectByStatusOrDesignStyleOrType(Pageable pageable, @RequestParam (name = "Status",required = false) String status, @RequestParam(name = "designStyleId", required = false, defaultValue = "0") int designStyleId, @RequestParam(name = "type", required = false,defaultValue = "0") int typeId){
        BaseResponse baseResponse = new BaseResponse();
        Page<ProjectWithUserDTO> projectDTOS = projectImp.findAllByStatusOrDesignStyleIdOrTypeProject_Id(status,designStyleId,typeId,pageable);
        if(!projectDTOS.isEmpty()){
            baseResponse.setData(projectDTOS);
            baseResponse.setTotalPages(projectDTOS.getTotalPages());
            baseResponse.setMesssage("Successfull");
            baseResponse.setStatusCode(200);
        }else{
            baseResponse.setData(null);
            baseResponse.setMesssage("Not Found");
            baseResponse.setStatusCode(400);
        }
        return  new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @GetMapping("/getAllQuoteByProjectId")
    public ResponseEntity<?> getAllQuoteByProjectId(@RequestParam int projectId){
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setMesssage("SucessFull");
        baseResponse.setStatusCode(200);
        ProjectWithAllQuoteDTO allQuoteRoomByProject = projectImp.findAllQuoteRoomByProject(projectId);
        if(allQuoteRoomByProject == null){
            baseResponse.setMesssage("Not Found");
            baseResponse.setStatusCode(400);
            baseResponse.setData(null);
            return new ResponseEntity<>(baseResponse, HttpStatus.NOT_FOUND);
        }else{
            baseResponse.setData(allQuoteRoomByProject);
            return new ResponseEntity<>(baseResponse, HttpStatus.OK);
        }
    }



    @PostMapping(value = "/createProject")
    public ResponseEntity<?> create( @RequestBody ProjectRequest projectRequest, @RequestParam int userId, @RequestParam String status){
        BaseResponse baseResponse = new BaseResponse();
        int check = projectImp.create(projectRequest,userId,status);
        if(check != 0){
            baseResponse.setStatusCode(201);
            baseResponse.setMesssage("Create Successfull");
            baseResponse.setData(check);
            return new ResponseEntity<>(baseResponse, HttpStatus.OK);
        }
        baseResponse.setStatusCode(404);
        baseResponse.setMesssage("Create Failed");
        baseResponse.setData(check);
        return new ResponseEntity<>(baseResponse,HttpStatus.NOT_FOUND);

    }

    @PostMapping(value = "/createSampleProject",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createSampleProject( @RequestPart(name = "fileImg", required = false) MultipartFile fileImg,
                                     @RequestParam String name,
                                     @RequestParam String location,
                                     @RequestParam boolean isSample,
                                     @RequestParam int designStyleId,
                                     @RequestParam int typeId,
                                     @RequestParam String status){
        BaseResponse baseResponse = new BaseResponse();
        int check = projectImp.createSampleProject(name, location, isSample, designStyleId, typeId,status,fileImg);
        if(check != 0){
            baseResponse.setStatusCode(201);
            baseResponse.setMesssage("Create Successfull");
            baseResponse.setData(check);
            return new ResponseEntity<>(baseResponse, HttpStatus.OK);
        }
        baseResponse.setStatusCode(404);
        baseResponse.setMesssage("Create Failed");
        baseResponse.setData(check);
        return new ResponseEntity<>(baseResponse,HttpStatus.NOT_FOUND);

    }

    @PostMapping(value = "/createBySampleProject")
    public ResponseEntity<?> createSample(@RequestBody ProjectSampleRequest projectSampleRequest){
        BaseResponse baseResponse = new BaseResponse();
        boolean check = projectImp.createBySampleProject(projectSampleRequest);
        if(check){
            baseResponse.setStatusCode(201);
            baseResponse.setMesssage("Create Successfull");
            baseResponse.setData(check);
            return new ResponseEntity<>(baseResponse, HttpStatus.OK);
        }
        baseResponse.setStatusCode(404);
        baseResponse.setMesssage("Create Failed");
        baseResponse.setData(check);
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

    @PutMapping(value = "/updateProjectByStatus")
    public ResponseEntity<?> update(@RequestParam int projectId, @RequestParam String status, @RequestParam(name = "userId", required = false, defaultValue = "0") int userId){
        boolean check = projectImp.updateProjectByStatus(projectId, userId, status);
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
    public ResponseEntity<?> getProjectById(@RequestParam int id){
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

//    @GetMapping("/getAllProjectByStatus")
//    public ResponseEntity<?> getProjectById(@RequestParam String status){
//        BaseResponse baseResponse = new BaseResponse();
//        baseResponse.setMesssage("SucessFull");
//        baseResponse.setStatusCode(200);
//        List<ProjectDTO> projectDTOS = projectImp.findByStatus(status);
//        if(projectDTOS == null){
//            baseResponse.setMesssage("Not Found");
//            baseResponse.setStatusCode(200);
//            baseResponse.setData(null);
//            return new ResponseEntity<>(baseResponse, HttpStatus.NOT_FOUND);
//        }else{
//            baseResponse.setData(projectDTOS);
//            return new ResponseEntity<>(baseResponse, HttpStatus.OK);
//        }
//
//
//    }


}
