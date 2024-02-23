package com.swp391.project.service.impl;

import com.swp391.project.entity.UserEntity;
import org.springframework.http.ResponseEntity;

public interface LoginServiceImp {
    UserEntity checkLoginGmail(String email);

    UserEntity checkLogin(String username, String password);
}
