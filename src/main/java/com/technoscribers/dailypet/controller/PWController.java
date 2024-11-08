package com.technoscribers.dailypet.controller;

import com.technoscribers.dailypet.exceptions.InvalidInfoException;
import com.technoscribers.dailypet.model.PWAvailabilityModel;
import com.technoscribers.dailypet.repository.entity.PWAvailability;
import com.technoscribers.dailypet.service.PWService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/service/pw")
//Pet Walker
public class PWController {

    @Autowired
    PWService pwService;
    @GetMapping("/availability")
    public ResponseEntity<List<PWAvailabilityModel>> getMyAvailability(@RequestParam Long serviceId) {
        return ResponseEntity.ok().body(pwService.getAvailability(serviceId));
    }
    @PostMapping("/availability")
    public ResponseEntity<?> createAvailability(@RequestBody PWAvailabilityModel inputModel){
        PWAvailabilityModel model = null;
        try {
            model = pwService.createAvailability(inputModel);
            return ResponseEntity.ok(model);

        } catch (InvalidInfoException e) {
            return ResponseEntity.badRequest().body(e.getLocalizedMessage());
        }
    }
}
