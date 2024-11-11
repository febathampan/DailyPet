package com.technoscribers.dailypet.service;

import com.technoscribers.dailypet.repository.BreedRepository;
import com.technoscribers.dailypet.repository.entity.Breed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BreedService {
    @Autowired
    BreedRepository breedRepository;

    public List<Breed> getAllBreeds(){
        return breedRepository.findAll();
    }
}
