package de.ait_tr.g_38_jp_shop.service;

import de.ait_tr.g_38_jp_shop.service.interfaces.FileService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

@Service
@Profile("dev")
public class FileServiceLocal implements FileService {

    private static final String LOCAL_PATH = "C:\\Users\\admin\\IdeaProjects\\shop.ait_jp\\file\\";

    @Override
    public String upload(MultipartFile file, String productTitle) {
        try {
            String uniqueFileName = generateUniqueFileName(file);
            Files.copy(
                    file.getInputStream(),
                    Path.of(LOCAL_PATH + uniqueFileName)
            );
            return uniqueFileName;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String generateUniqueFileName(MultipartFile file) {
        String originalName = file.getOriginalFilename();

        if (originalName == null) {
            throw new RuntimeException("Original file name is empty");
        }

        int lastDotIndex = originalName.lastIndexOf(".");
        String fileName = originalName.substring(0, lastDotIndex);
        String extension = originalName.substring(lastDotIndex);

        return String.format("%s-%s%s", fileName, UUID.randomUUID(), extension);
    }
}
