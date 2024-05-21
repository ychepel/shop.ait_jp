package de.ait_tr.g_38_jp_shop.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import de.ait_tr.g_38_jp_shop.service.interfaces.FileService;
import de.ait_tr.g_38_jp_shop.service.interfaces.ProductService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
@Profile("prod")
public class FileServiceDigitalOcean implements FileService {

    private AmazonS3 client;
    private ProductService productService;

    public FileServiceDigitalOcean(AmazonS3 client, ProductService productService) {
        this.client = client;
        this.productService = productService;
    }

    @Override
    public String upload(MultipartFile file, String productTitle) {
        try {
            String uniqueFileName = generateUniqueFileName(file);

            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(file.getContentType());

            PutObjectRequest request = new PutObjectRequest(
                    "cohort-38-bucket",
                    uniqueFileName,
                    file.getInputStream(),
                    metadata
            ).withCannedAcl(CannedAccessControlList.PublicRead);

            client.putObject(request);

            String url = client.getUrl("cohort-38-bucket", uniqueFileName).toString();

            productService.attachImage(url, productTitle);

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
