package com.technoscribers.dailypet.controller;

import com.technoscribers.dailypet.exceptions.InvalidInfoException;
import com.technoscribers.dailypet.exceptions.UnableToPersistException;
import com.technoscribers.dailypet.model.PetDetailsModel;
import com.technoscribers.dailypet.service.PetProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pet")
public class PetController {

    @Autowired
    PetProfileService petService;

    @PostMapping("/register")
    public ResponseEntity<?> registerPet(@RequestBody PetDetailsModel petDetailsModel){
        try{
            PetDetailsModel savedPet = petService.savePet(petDetailsModel);
            return ResponseEntity.ok().body(savedPet);
        }catch (InvalidInfoException | UnableToPersistException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/alert")
    public ResponseEntity<String> lostPet(@RequestParam Long petId){
        Boolean alertActive = null;
        try {
            alertActive = petService.lostPet(petId);
        } catch (InvalidInfoException e) {
            return ResponseEntity.badRequest().body("Failed");
        }
        return ResponseEntity.ok().body(alertActive?"Success": "Failed");
    }
}
