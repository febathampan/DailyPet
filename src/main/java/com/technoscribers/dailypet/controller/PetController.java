package com.technoscribers.dailypet.controller;

import com.technoscribers.dailypet.exceptions.IncompleteInfoException;
import com.technoscribers.dailypet.exceptions.UnableToPersistException;
import com.technoscribers.dailypet.model.PetDetailsModel;
import com.technoscribers.dailypet.service.PetProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/pets")
public class PetController {

    @Autowired
    PetProfileService petService;

    @PostMapping("/pet")
    public ResponseEntity<?> registerPet(@RequestBody PetDetailsModel petDetailsModel, @RequestPart(value = "image", required = false)MultipartFile image){
        try{
            PetDetailsModel savedPet = petService.savePet(petDetailsModel, image);
            return ResponseEntity.ok().body(savedPet);
        }catch (IncompleteInfoException | UnableToPersistException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
