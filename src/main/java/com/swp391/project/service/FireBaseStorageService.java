package com.swp391.project.service;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.firebase.cloud.StorageClient;
import com.swp391.project.config.FirebaseConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Service
public class FireBaseStorageService {

    @Autowired
    private FirebaseConfig firebaseConfig;

    public String uploadImage(MultipartFile file) throws IOException {
        Storage storage = StorageClient.getInstance(firebaseConfig.firebaseApp()).bucket().getStorage();

        String bucketName = "swp391-f7197.appspot.com";
        String fileName = "images/" + file.getOriginalFilename();

        BlobId blobId = BlobId.of(bucketName, fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(file.getContentType()).build();
        Blob blob = storage.create(blobInfo, file.getBytes());

        // Lấy đường dẫn có quyền truy cập
        String downloadUrl = blob.getMediaLink();

        // Đặt quyền truy cập hết hạn sau một khoảng thời gian (ví dụ: 1 giờ)
        String signedUrl = blob.signUrl(730 * 3, TimeUnit.HOURS).toString();

        return signedUrl;
    }
}
