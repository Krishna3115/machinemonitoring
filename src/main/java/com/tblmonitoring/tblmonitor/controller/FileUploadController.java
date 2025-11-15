package com.tblmonitoring.tblmonitor.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

//@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/upload")
public class FileUploadController {

	private static final String UPLOAD_DIR = "uploads/";

	@PostMapping("/image")
    public ResponseEntity<Map<String, String>> uploadImage(@RequestParam("file") MultipartFile file) throws IOException {
        validateFile(file, List.of("image/jpeg", "image/png", "image/webp", "image/jpg"));
        String fileName = saveFile(file);
        String fileUrl = "/files/" + fileName;
        return ResponseEntity.ok(Map.of("url", fileUrl));
    }
	
	
    @PostMapping("/pdf")
    public ResponseEntity<Map<String, String>> uploadPdf(@RequestParam("file") MultipartFile file) throws IOException {
        validateFile(file, List.of("application/pdf"));

        String fileName = saveFile(file);
        String fileUrl = "/files/" + fileName;

        return ResponseEntity.ok(Map.of("url", fileUrl));
    }

    private void validateFile(MultipartFile file, List<String> allowedMimeTypes) {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }

        String mimeType = file.getContentType();
        if (!allowedMimeTypes.contains(mimeType)) {
            throw new IllegalArgumentException("Invalid file type: " + mimeType);
        }
    }

    private String saveFile(MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        String extension = Objects.requireNonNull(originalFilename).substring(originalFilename.lastIndexOf("."));
        String fileName = UUID.randomUUID() + extension;

        Path path = Paths.get(UPLOAD_DIR + fileName);
        Files.createDirectories(path.getParent());
        Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

        return fileName;
    }
}
