package com.technoscribers.dailypet.service;

import com.technoscribers.dailypet.exceptions.UnableToPersistException;
import com.technoscribers.dailypet.model.MedicationModel;
import com.technoscribers.dailypet.model.VaccineModel;
import com.technoscribers.dailypet.repository.MedicationRepository;
import com.technoscribers.dailypet.repository.PetDetailsRepository;
import com.technoscribers.dailypet.repository.entity.Medication;
import com.technoscribers.dailypet.repository.entity.PetDetails;
import com.technoscribers.dailypet.repository.entity.Vaccine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MedicationService {

    @Autowired
    PetDetailsRepository petDetailsRepository;

    @Autowired
    MedicationRepository medicationRepository;

    public List<MedicationModel> saveMedicationForPet(List<MedicationModel> medicationModels, Optional<PetDetails> petDetails) throws UnableToPersistException {
        List<MedicationModel> medications = new ArrayList<>();
        for (MedicationModel m : medicationModels) {
            MedicationModel result = saveMedicationForPet(m, petDetails);
            medications.add(result);
        }
        return medications;
    }

    public MedicationModel saveMedicationForPet(MedicationModel medicationModel, Optional<PetDetails> petDetails) throws UnableToPersistException {
        if(petDetails.isEmpty()) {
            //Assuming incoming model has petDetails id
            petDetails = petDetailsRepository.findById(medicationModel.getPetId());
        }
        if (petDetails.isEmpty()) {
            throw new UnableToPersistException("PetDetails not found!");
        }

        Medication medication = new Medication(medicationModel.getName(), medicationModel.getStart(), medicationModel.getEnd(),
                Boolean.TRUE, petDetails.get());

        Medication m = medicationRepository.save(medication);
        try {
            medicationModel.setId(m.getId());
        } catch (Exception e) {
            throw new UnableToPersistException("Medication not saved");
        }
        return medicationModel;
    }

}
