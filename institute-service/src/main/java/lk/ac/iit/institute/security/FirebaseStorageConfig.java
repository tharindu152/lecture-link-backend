package lk.ac.iit.institute.security;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Bucket;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.StorageClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
@ConditionalOnProperty(prefix = "application.firebase", name = "enabled", havingValue = "true")
public class FirebaseStorageConfig {
    @Bean
    Bucket firebaseBucket(@Value("${application.firebase.storage.bucket}") String bucketName)
            throws IOException {
        FirebaseApp app;
        if (FirebaseApp.getApps().isEmpty()) {
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.getApplicationDefault())
                    .setStorageBucket(bucketName)
                    .build();
            app = FirebaseApp.initializeApp(options);
        } else {
            app = FirebaseApp.getInstance();
        }
        return StorageClient.getInstance(app).bucket(bucketName);
    }
}
