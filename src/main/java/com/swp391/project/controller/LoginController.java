package com.swp391.project.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.swp391.project.service.LoginService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @Operation(summary = "This is to login the website")
    @ApiResponses(value = {

            @ApiResponse(responseCode = "200",
            description = "Login successful",
            content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400",
            description = "Failed",
            content = @Content)
    })


    @GetMapping("/signin")
    public ResponseEntity<?> message(){
        String mess = "Hello World";
        return new ResponseEntity<>(mess, HttpStatus.OK);
    }

    @PostMapping("/signin/gmail")
    public ResponseEntity<?> loginWithGmail(@RequestParam String accessToken) {
        return loginService.loginWithGmail(accessToken);
    }

}
