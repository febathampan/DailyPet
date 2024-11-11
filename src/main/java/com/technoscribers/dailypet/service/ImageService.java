package com.technoscribers.dailypet.service;

import com.azure.storage.blob.BlobClientBuilder;
import com.azure.storage.blob.models.BlobHttpHeaders;
import com.technoscribers.dailypet.repository.ImageRepository;
import com.technoscribers.dailypet.repository.entity.Images;
import com.technoscribers.dailypet.repository.entity.PetDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Blob;
import java.util.UUID;

@Service
public class ImageService {
    @Autowired
    ImageRepository imageRepository;

    @Value("${azure.storage.connection-string}")
    private String connectionString;

    @Value("${azure.storage.container-name}")
    private String containerName;

    public Images saveImage(Blob input, PetDetails petDetails) {
        Images images = new Images();
        images.setImage(input);
        images.setPetDetails(petDetails);
        return imageRepository.save(images);
    }

    public String uploadImage(MultipartFile file) throws IOException {
        String fileName = "" + UUID.randomUUID()+"_"+file.getOriginalFilename();

        // Initialize Blob Client
        var blobClient = new BlobClientBuilder()
                .connectionString(connectionString)
                .containerName(containerName)
                .blobName(fileName)
                .buildClient();

        // Upload the file
        blobClient.upload(file.getInputStream(), file.getSize(), true);

        // Set content type for the blob
        blobClient.setHttpHeaders(new BlobHttpHeaders().setContentType(file.getContentType()));

        // Return the URI of the uploaded image
        return blobClient.getBlobUrl();
    }

}
