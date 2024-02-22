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

//        ClassLoader classLoader = ProjectApplication.class.getClassLoader();
//        FileInputStream serviceAccount = new FileInputStream(resource.getFile());
        Resource resource = new ClassPathResource("serviceAccountKey.json");
        InputStream inputStream = resource.getInputStream();
//        InputStream serviceAccount = new ClassPathResource("serviceAccountKey.json").getInputStream();

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(Objects.requireNonNull(inputStream)))
                .setServiceAccountId("firebase-adminsdk-qokx0@swp391-f7197.iam.gserviceaccount.com")
                .setStorageBucket(storageBucket)
                .build();

        return FirebaseApp.initializeApp(options);
    }
}
