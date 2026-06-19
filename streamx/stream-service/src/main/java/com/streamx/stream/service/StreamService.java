package com.streamx.stream.service;

import io.minio.*;
import io.minio.http.Method;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class StreamService {

    private final MinioClient minioClient;

    @Value("${minio.bucket}")
    private String bucket;

    public String uploadVideo(MultipartFile file) {
        try {
            String key = UUID.randomUUID() + "-" + file.getOriginalFilename();

            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucket)
                            .object(key)
                            .stream(file.getInputStream(), file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build()
            );

            log.info("Video uploaded: {}", key);
            return key;

        } catch (Exception e) {
            log.error("Upload failed: {}", e.getMessage());
            throw new RuntimeException("Video upload failed: " + e.getMessage());
        }
    }

    public InputStream getVideoStream(String key) {
        try {
            return minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(bucket)
                            .object(key)
                            .build()
            );
        } catch (Exception e) {
            log.error("Stream failed: {}", e.getMessage());
            throw new RuntimeException("Video not found: " + key);
        }
    }

    public String getPresignedUrl(String key) {
        try {
            return minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .bucket(bucket)
                            .object(key)
                            .method(Method.GET)
                            .expiry(60 * 60)
                            .build()
            );
        } catch (Exception e) {
            throw new RuntimeException("Could not generate URL: " + e.getMessage());
        }
    }

    public StatObjectResponse getVideoInfo(String key) {
        try {
            return minioClient.statObject(
                    StatObjectArgs.builder()
                            .bucket(bucket)
                            .object(key)
                            .build()
            );
        } catch (Exception e) {
            throw new RuntimeException("Video not found: " + key);
        }
    }

    public InputStream getVideoRange(String key, long start, long end) {
        try {
            return minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(bucket)
                            .object(key)
                            .offset(start)
                            .length(end - start + 1)
                            .build()
            );
        } catch (Exception e) {
            throw new RuntimeException("Failed to stream range: " + e.getMessage());
        }
    }
}