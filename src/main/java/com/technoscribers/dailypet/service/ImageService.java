package com.technoscribers.dailypet.service;

import com.technoscribers.dailypet.repository.ImageRepository;
import com.technoscribers.dailypet.repository.entity.Images;
import com.technoscribers.dailypet.repository.entity.PetDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Blob;

@Service
public class ImageService {
    @Autowired
    ImageRepository imageRepository;

    public Images saveImage(Blob input, PetDetails petDetails) {
        Images images = new Images();
        images.setImage(input);
        images.setPetDetails(petDetails);
        return imageRepository.save(images);
    }
}
