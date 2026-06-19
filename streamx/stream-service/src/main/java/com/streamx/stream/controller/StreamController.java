package com.streamx.stream.controller;


import com.streamx.stream.service.StreamService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/stream")
@RequiredArgsConstructor
@Tag(name = "Stream", description = "Video upload and streaming")
public class StreamController {

    private final StreamService streamService;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Upload a video file")
    public ResponseEntity<Map<String, String>> upload(
            @RequestParam("file") MultipartFile file,
            @RequestHeader("X-User-Id") String userId) {

        String key = streamService.uploadVideo(file);
        return ResponseEntity.ok(Map.of(
                "videoKey", key,
                "uploadedBy", userId,
                "message", "Upload successful"
        ));
    }

    @GetMapping("/{key}")
    @Operation(summary = "Stream video by key")
    public ResponseEntity<InputStreamResource> stream(
            @PathVariable String key) {

        InputStream stream = streamService.getVideoStream(key);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("video/mp4"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline")
                .body(new InputStreamResource(stream));
    }

    @GetMapping("/url/{key}")
    @Operation(summary = "Get presigned URL for video")
    public ResponseEntity<Map<String, String>> getUrl(@PathVariable String key) {
        String url = streamService.getPresignedUrl(key);
        return ResponseEntity.ok(Map.of("url", url));
    }
}