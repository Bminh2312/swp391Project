package com.swp391.project.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {


    @Operation(summary = "This is to login the website")
    @ApiResponses(value = {

            @ApiResponse(responseCode = "200",
            description = "Login successful",
            content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400",
            description = "Failed",
            content = @Content)
    })


    @GetMapping("signin")
    public ResponseEntity<?> message(){
        String mess = "Hello World";
        return new ResponseEntity<>(mess, HttpStatus.OK);
    }

}
