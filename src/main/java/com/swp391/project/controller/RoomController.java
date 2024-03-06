package com.swp391.project.controller;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/room")
@CrossOrigin(origins = {"http://localhost:8082", "https://furniture-quote.azurewebsites.net",
        "https://eclectic-belekoy-79d097.netlify.app/", "http://localhost:3000"}, allowCredentials = "true")
public class RoomController {

}
