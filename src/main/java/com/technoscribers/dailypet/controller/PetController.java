package com.technoscribers.dailypet.controller;

import com.technoscribers.dailypet.exceptions.IncompleteInfoException;
import com.technoscribers.dailypet.exceptions.UnableToPersistException;
import com.technoscribers.dailypet.model.PetDetailsModel;
import com.technoscribers.dailypet.service.PetProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pets")
public class PetController {

    @Autowired
    PetProfileService petService;

    @PostMapping("/pet")
    public ResponseEntity<?> registerPet(@RequestBody PetDetailsModel petDetailsModel){
        try{
            PetDetailsModel savedPet = petService.savePet(petDetailsModel);
            return ResponseEntity.ok().body(savedPet);
        }catch (IncompleteInfoException | UnableToPersistException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
