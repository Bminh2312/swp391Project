package com.swp391.project.service;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.firebase.cloud.StorageClient;
import com.swp391.project.config.FirebaseConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Service
public class FireBaseStorageService {

    @Value("${firebase.storage.bucket}")
    private String storageBucket;

    @Autowired
    private FirebaseConfig firebaseConfig;

    public String uploadImage(MultipartFile file) throws IOException {
        Storage storage = StorageClient.getInstance().bucket().getStorage();

        String fileName = "images/" + file.getOriginalFilename();

        BlobId blobId = BlobId.of(storageBucket, fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(file.getContentType()).build();
        Blob blob = storage.create(blobInfo, file.getBytes());

        // Lấy đường dẫn có quyền truy cập
        String downloadUrl = blob.getMediaLink();

        // Đặt quyền truy cập hết hạn sau một khoảng thời gian (ví dụ: 1 giờ)
        String signedUrl = blob.signUrl(730 * 3, TimeUnit.HOURS).toString();

        return signedUrl;
    }

    public boolean deleteFile(String url) {
        String fileName = getFileNameFromUrl(url);
        StorageClient storageClient = StorageClient.getInstance();

        return storageClient.bucket(storageBucket).get(fileName).delete();
    }

    private String getFileNameFromUrl(String url) {
        int lastSlashIndex = url.lastIndexOf('/');
        if (lastSlashIndex != -1 && lastSlashIndex < url.length() - 1) {
            return url.substring(lastSlashIndex + 1).replace("?alt=media", "");
        } else {
            return null;
        }
    }
}
