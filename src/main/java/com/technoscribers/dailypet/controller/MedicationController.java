package com.technoscribers.dailypet.controller;

import com.technoscribers.dailypet.exceptions.UnableToPersistException;
import com.technoscribers.dailypet.model.MedicationModel;
import com.technoscribers.dailypet.model.VaccineModel;
import com.technoscribers.dailypet.service.MedicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/medication")
public class MedicationController {

    @Autowired
    MedicationService medicationService;

    @PostMapping()
    public ResponseEntity<?> addMedication(@RequestBody MedicationModel model) {
        try {
            MedicationModel result = medicationService.saveMedicationForPet(model, null);
            return ResponseEntity.ok().body(result);
        } catch (UnableToPersistException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/all")
    public ResponseEntity<?> addMedication(@RequestBody List<MedicationModel> models) {
        try {
            List<MedicationModel> results = medicationService.saveMedicationForPet(models, null);
            return ResponseEntity.ok().body(results);
        } catch (UnableToPersistException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


}
