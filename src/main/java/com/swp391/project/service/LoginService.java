package com.swp391.project.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.swp391.project.config.FirebaseConfig;
import com.swp391.project.dto.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    public ResponseEntity<?> loginWithGmail(String accessToken) {
        try{
            System.out.println("Token: " + accessToken);
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(accessToken);
            String email = decodedToken.getEmail();
            String name = decodedToken.getName();
            System.out.println("Email: " + decodedToken.getEmail());
//            ObjectMapper mapper = new ObjectMapper();
            Account account = new Account();
            account.setEmail(email);
            account.setName(name);
            return new ResponseEntity<>(decodedToken, HttpStatus.OK);
        } catch (FirebaseAuthException e) {
            System.out.println("Firebase Authentication Error: " + e.getMessage());
            return new ResponseEntity<>(e.getMessage(), null, HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return new ResponseEntity<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }
}
