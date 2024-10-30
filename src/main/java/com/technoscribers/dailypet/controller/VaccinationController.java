package com.technoscribers.dailypet.controller;

import com.technoscribers.dailypet.exceptions.UnableToPersistException;
import com.technoscribers.dailypet.model.VaccineModel;

import com.technoscribers.dailypet.service.VaccineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/vaccine")
public class VaccinationController {

    @Autowired
    VaccineService vaccineService;

    @PostMapping()
    public ResponseEntity<?> addVaccine(@RequestBody VaccineModel vaccineModel) {
        try {
            VaccineModel savedPet = vaccineService.saveVaccineForPet(vaccineModel, null);
            return ResponseEntity.ok().body(savedPet);
        } catch (UnableToPersistException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/all")
    public ResponseEntity<?> addVaccines(@RequestBody List<VaccineModel> vaccineModels) {
        try {
            List<VaccineModel> savedVaccines = vaccineService.saveVaccineForPet(vaccineModels, null);
            return ResponseEntity.ok().body(savedVaccines);
        } catch (UnableToPersistException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
