package io.hauyu07.orderingservice;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileInputStream;
import java.io.IOException;

@SpringBootApplication
public class OrderingServiceApplication {

    public static void main(String[] args) throws IOException {
        FileInputStream serviceAccount =
                new FileInputStream("src/main/resources/firebase-credentials.json");

        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();

        FirebaseApp.initializeApp(options);
        
        SpringApplication.run(OrderingServiceApplication.class, args);
    }

}
