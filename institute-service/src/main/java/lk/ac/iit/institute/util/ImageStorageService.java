package lk.ac.iit.institute.util;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class ImageStorageService {
    private final ObjectProvider<Bucket> bucketProvider;

    public ImageStorageService(ObjectProvider<Bucket> bucketProvider) {
        this.bucketProvider = bucketProvider;
    }

    public String store(String directory, MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            return null;
        }
        if (file.getContentType() == null || !file.getContentType().toLowerCase().startsWith("image/")) {
            throw new IllegalArgumentException("Uploaded file must be an image");
        }
        Bucket bucket = getBucket();
        String path = directory + "/" + UUID.randomUUID();
        String contentType = file.getContentType() == null ? "application/octet-stream" : file.getContentType();
        bucket.create(path, file.getInputStream(), contentType);
        return path;
    }

    public String getUrl(String path) {
        if (path == null || path.isBlank() || path.startsWith("http://") || path.startsWith("https://")) {
            return path;
        }
        Blob blob = getBucket().get(path);
        if (blob == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Image not found");
        }
        return blob.signUrl(1, TimeUnit.DAYS, Storage.SignUrlOption.withV4Signature()).toString();
    }

    public void delete(String path) {
        if (path == null || path.isBlank() || path.startsWith("http://") || path.startsWith("https://")) {
            return;
        }
        Blob blob = getBucket().get(path);
        if (blob != null) {
            blob.delete();
        }
    }

    private Bucket getBucket() {
        Bucket bucket = bucketProvider.getIfAvailable();
        if (bucket == null) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,
                    "Firebase image storage is not configured");
        }
        return bucket;
    }
}
