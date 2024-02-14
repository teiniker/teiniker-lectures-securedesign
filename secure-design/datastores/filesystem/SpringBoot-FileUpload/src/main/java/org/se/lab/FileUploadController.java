package org.se.lab;

import java.nio.file.*;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

@RestController
public class FileUploadController
{
    @Value("${file.upload-dir}")
    private String uploadDir;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file)
    {
        if (file.isEmpty())
        {
            return ResponseEntity.badRequest().body("File is empty");
        }

        try
        {
            // Ensure the upload directory exists
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath))
            {
                Files.createDirectories(uploadPath);
            }

            // Save the file on the server
            Path filePath = uploadPath.resolve(file.getOriginalFilename());
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            String response = "Received file: " + file.getOriginalFilename()
                + " with size: " + file.getSize() + " bytes and saved it to "
                    + filePath.toAbsolutePath();
            return ResponseEntity.ok(response);
        }
        catch (IOException e)
        {
            return ResponseEntity.internalServerError().body("Could not upload the file: " + file.getOriginalFilename());
        }
    }
}
