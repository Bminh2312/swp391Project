package com.swp391.project.controller;


import com.swp391.project.dto.ProjectDTO;
import com.swp391.project.dto.ProjectWithAllQuoteDTO;
import com.swp391.project.dto.QuoteDetailDTO;
import com.swp391.project.payload.request.QuoteDetailForRawRequest;
import com.swp391.project.payload.request.QuoteRequest;
import com.swp391.project.payload.response.BaseResponse;
import com.swp391.project.service.impl.QuoteServiceImp;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/quote")
@CrossOrigin(origins = {"http://localhost:8082", "https://furniture-quote.azurewebsites.net",
        "https://eclectic-belekoy-79d097.netlify.app/", "http://localhost:3000"}, allowCredentials = "true")
public class QuoteController {

    @Autowired
    private QuoteServiceImp quoteServiceImp;

    @GetMapping("/getAllQuoteByProjectSample")
    public ResponseEntity<?> getAllQuoteByProjectSample(@RequestParam(required = false, defaultValue = "true") @Schema(description = "isSample", type = "boolean") boolean isSample){
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setMesssage("SucessFull");
        baseResponse.setStatusCode(200);
        List<ProjectDTO> projectDTOS = quoteServiceImp.findAllQuoteRoomByProject(isSample);
        if(projectDTOS == null){
            baseResponse.setMesssage("Not Found");
            baseResponse.setStatusCode(400);
            baseResponse.setData(null);
            return new ResponseEntity<>(baseResponse, HttpStatus.NOT_FOUND);
        }else{
            baseResponse.setData(projectDTOS);
            return new ResponseEntity<>(baseResponse, HttpStatus.OK);
        }
    }

    @GetMapping("/getAllQuoteByProjectId")
    public ResponseEntity<?> getQuoteByProjectId(@RequestParam int projectId){
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setMesssage("SucessFull");
        baseResponse.setStatusCode(200);
        ProjectWithAllQuoteDTO project = quoteServiceImp.findQuoteRoomByProjectId(projectId);
        if(project == null){
            baseResponse.setMesssage("Not Found");
            baseResponse.setStatusCode(400);
            baseResponse.setData(null);
            return new ResponseEntity<>(baseResponse, HttpStatus.NOT_FOUND);
        }else{
            baseResponse.setData(project);
            return new ResponseEntity<>(baseResponse, HttpStatus.OK);
        }
    }

    @GetMapping("/getQrToPayByProjectId")
    public ResponseEntity<?> getQrToPayByProjectId(@RequestParam int projectId, @RequestParam String description){
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setMesssage("SucessFull");
        baseResponse.setStatusCode(200);
        String qr = quoteServiceImp.getQrToPay(projectId,description);
        if(quoteServiceImp == null){
            baseResponse.setMesssage("Project not found or No quote available");
            baseResponse.setStatusCode(400);
            baseResponse.setData(null);
            return new ResponseEntity<>(baseResponse, HttpStatus.NOT_FOUND);
        }else{
            baseResponse.setData(qr);
            return new ResponseEntity<>(baseResponse, HttpStatus.OK);
        }
    }

    @PutMapping("/updateTotalQuote")
    public ResponseEntity<?> updateTotalQuote(@RequestParam int projectId){
        boolean check = quoteServiceImp.updateTotal(projectId);
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

    @PostMapping(value = "/createQuoteForProject")
    public ResponseEntity<?> createQuoteForProject(@RequestBody QuoteRequest quoteRequest , @RequestParam String status){
        BaseResponse baseResponse = new BaseResponse();
        int check = quoteServiceImp.create(quoteRequest,status);
        if(check != 0){
            baseResponse.setStatusCode(201);
            baseResponse.setMesssage("Create Successfull");
            baseResponse.setData(check);
            return new ResponseEntity<>(baseResponse, HttpStatus.OK);
        }
        baseResponse.setStatusCode(200);
        baseResponse.setMesssage("Create Failed");
        baseResponse.setData(check);
        return new ResponseEntity<>(baseResponse,HttpStatus.NOT_FOUND);

    }

    @PostMapping(value = "/createQuoteForProjectSample",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createQuoteForProjectSample(   @RequestPart(name = "fileImg", required = false) MultipartFile fileImg,
                                                            @RequestParam int projectId,
                                                            @RequestParam int roomId,
                                                            @RequestParam double area ,
                                                            @RequestParam String status){
        BaseResponse baseResponse = new BaseResponse();
        int check = quoteServiceImp.createSampleQuote(projectId,roomId,area,status,fileImg);
        if(check != 0){
            baseResponse.setStatusCode(201);
            baseResponse.setMesssage("Create Successfull");
            baseResponse.setData(check);
            return new ResponseEntity<>(baseResponse, HttpStatus.OK);
        }
        baseResponse.setStatusCode(200);
        baseResponse.setMesssage("Create Failed");
        baseResponse.setData(check);
        return new ResponseEntity<>(baseResponse,HttpStatus.NOT_FOUND);

    }

    @PutMapping(value = "/updateQuoteForProject")
    public ResponseEntity<?> updateQuoteForProject(@RequestBody QuoteRequest quoteRequest , @RequestParam int id, @RequestParam String status){
        boolean check = quoteServiceImp.update(quoteRequest,id,status);
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
}
