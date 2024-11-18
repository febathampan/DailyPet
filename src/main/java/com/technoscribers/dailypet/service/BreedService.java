package com.technoscribers.dailypet.service;

import com.technoscribers.dailypet.model.BreedModel;
import com.technoscribers.dailypet.repository.BreedRepository;
import com.technoscribers.dailypet.repository.entity.Breed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BreedService {
    @Autowired
    BreedRepository breedRepository;

    public List<BreedModel> getAllBreeds(){
        return breedRepository.findAll().stream().map(b -> new BreedModel(b.getId(), b.getLifeSpan(), b.getTemperament(), b.getBreedName(),
                b.getPetType().getType(), b.getPetType().getCategory(), b.getDescription())).collect(Collectors.toList());
    }

    public BreedModel getModel(Breed b){
        return new BreedModel(b.getId(), b.getLifeSpan(), b.getTemperament(), b.getDescription(),
                b.getPetType().getType(), b.getPetType().getCategory(), b.getDescription());
    }
}
