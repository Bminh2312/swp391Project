package com.swp391.project.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Configuration
public class FirebaseConfig {

    @Bean
    public FirebaseApp firebaseApp () throws IOException {
//        FileInputStream serviceAccount = new FileInputStream("classpath:swp391-f7197-firebase-adminsdk-qokx0-96c04d23a0.json");
        InputStream serviceAccount = getClass().getResourceAsStream("/swp391-f7197-firebase-adminsdk-qokx0-96c04d23a0.json");
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setStorageBucket("swp391-f7197.appspot.com")
                .build();

        return FirebaseApp.initializeApp(options);
    }
}
