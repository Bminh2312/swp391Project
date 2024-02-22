package com.swp391.project.controller;

import com.swp391.project.service.FireBaseStorageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
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
    public ResponseEntity<?> uploadImage(@RequestPart(name = "file", required = true) MultipartFile file){
        try {
            // Gọi service để tải lên Firebase Storage
            String imageUrl = fireBaseStorageService.uploadImage(file);

            // Trả về URL của ảnh sau khi tải lên
            return ResponseEntity.ok("Image uploaded successfully. URL: " + imageUrl);
        } catch (Exception e) {
            e.printStackTrace();
            // Xử lý lỗi và trả về thông báo lỗi
            return ResponseEntity.status(500).body("Error uploading image.");
        }
    }
}
