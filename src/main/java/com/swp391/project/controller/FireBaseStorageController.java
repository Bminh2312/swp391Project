package com.swp391.project.controller;

import com.swp391.project.payload.response.BaseResponse;
import com.swp391.project.service.FireBaseStorageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin(origins = {"http://localhost:8082", "https://furniture-quote.azurewebsites.net",
        "https://eclectic-belekoy-79d097.netlify.app/", "http://localhost:3000"}, allowCredentials = "true")
@RequestMapping("/pushImg")
public class FireBaseStorageController {

    @Autowired
    private FireBaseStorageService fireBaseStorageService;

    @Operation(summary = "Upload image to Firebase Storage")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Image uploaded successfully",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))}),
            @ApiResponse(responseCode = "500",
                    description = "Error uploading image",
                    content = @Content)
    })
    @PostMapping(value = "/upload",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadImage(@RequestPart(name = "file", required = true) MultipartFile file, @ModelAttribute int id){
        try {
            // Gọi service để tải lên Firebase Storage
            System.out.println(id);
            String imageUrl = fireBaseStorageService.uploadImage(file);
            BaseResponse baseResponse = new BaseResponse();
            baseResponse.setData(imageUrl);
            baseResponse.setMesssage("Image uploaded successfully");
            baseResponse.setStatusCode(201);
            // Trả về URL của ảnh sau khi tải lên
            return new ResponseEntity<>(baseResponse, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            // Xử lý lỗi và trả về thông báo lỗi
            return ResponseEntity.status(500).body("Error uploading image.");
        }
    }
}