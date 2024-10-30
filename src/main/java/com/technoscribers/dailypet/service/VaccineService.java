package com.technoscribers.dailypet.service;

import com.technoscribers.dailypet.exceptions.UnableToPersistException;
import com.technoscribers.dailypet.model.VaccineModel;
import com.technoscribers.dailypet.repository.PetDetailsRepository;
import com.technoscribers.dailypet.repository.VaccineRepository;
import com.technoscribers.dailypet.repository.entity.PetDetails;
import com.technoscribers.dailypet.repository.entity.Vaccine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class VaccineService {

    @Autowired
    VaccineRepository vaccineRepository;

    @Autowired
    PetDetailsRepository petDetailsRepository;

    public VaccineModel saveVaccineForPet(VaccineModel vaccineModel, Optional<PetDetails> petDetails) throws UnableToPersistException {
        if(petDetails.isEmpty()) {
            //If input detail is null, then model has petDetailsId
            petDetails = petDetailsRepository.findById(vaccineModel.getPetId());
        }
        if (petDetails.isEmpty()) {
            throw new UnableToPersistException("PetDetails not found!");
        }
        Vaccine vaccine = new Vaccine(vaccineModel.getName(), vaccineModel.getScheduledDate(), vaccineModel.getPreviousDate(),
                vaccineModel.getDescription(), Boolean.TRUE, petDetails.get());

        Vaccine  v  = vaccineRepository.save(vaccine);
        try {
            vaccineModel.setId(v.getId());
        }catch (Exception e){
            throw new UnableToPersistException("Vaccine not saved");
        }
        return vaccineModel;
    }
    public List<VaccineModel> saveVaccineForPet(List<VaccineModel> vaccineModels, PetDetails petDetails) throws UnableToPersistException {
        List<VaccineModel> savedVaccines = new ArrayList<>();
        for(VaccineModel v: vaccineModels){
            VaccineModel result = saveVaccineForPet(v, Optional.ofNullable(petDetails));
            savedVaccines.add(result);
        }
        return savedVaccines;
    }
    }
