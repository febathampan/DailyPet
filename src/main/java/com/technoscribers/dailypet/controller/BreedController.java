package com.technoscribers.dailypet.controller;

import com.technoscribers.dailypet.model.BreedModel;
import com.technoscribers.dailypet.service.BreedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/breed")
public class BreedController {
    @Autowired
    BreedService breedService;

    @GetMapping()
    public ResponseEntity<List<BreedModel>> getAllBreeds() {
        return ResponseEntity.ok().body(breedService.getAllBreeds());
    }
    }
