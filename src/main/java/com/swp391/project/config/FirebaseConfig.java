package com.swp391.project.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.swp391.project.ProjectApplication;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

@Configuration
public class FirebaseConfig {

    @Value("${firebase.storage.bucket}")
    private String storageBucket;

    @Value("classpath:serviceAccountKey.json")
    private Resource resource;

    @PostConstruct
    public FirebaseApp firebaseApp () throws IOException {
        InputStream serviceAccount = resource.getInputStream();
//        InputStream serviceAccount = new ClassPathResource("serviceAccountKey.json").getInputStream();

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(Objects.requireNonNull(serviceAccount)))
                .setServiceAccountId("firebase-adminsdk-qokx0@swp391-f7197.iam.gserviceaccount.com")
                .setStorageBucket(storageBucket)
                .build();

        return FirebaseApp.initializeApp(options);
    }
}