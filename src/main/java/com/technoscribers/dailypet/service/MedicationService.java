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
import java.util.stream.Collectors;

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

    public List<MedicationModel> editMedicationForPet(List<MedicationModel> medicationModels, Optional<PetDetails> petDetails) throws UnableToPersistException {
        //Delete previous medication
        List<Medication> prevMedications = medicationRepository.findByPetIdAndIsActiveIsTrue(petDetails.get().getId());
        medicationRepository.deleteAll(prevMedications);

        return saveMedicationForPet(medicationModels, petDetails);
    }

    public MedicationModel saveMedicationForPet(MedicationModel medicationModel, Optional<PetDetails> petDetails) throws UnableToPersistException {
        if (petDetails.isEmpty()) {
            //Assuming incoming model has petDetails id
            petDetails = petDetailsRepository.findById(medicationModel.getPetId());
        }
        if (petDetails.isEmpty()) {
            throw new UnableToPersistException("PetDetails not found!");
        }

        Medication medication = new Medication(medicationModel.getName(), medicationModel.getStart(), medicationModel.getEnd(),
                Boolean.TRUE, petDetails.get(), medicationModel.getDosageInstructions());

        Medication m = medicationRepository.save(medication);
        try {
            medicationModel.setId(m.getId());
        } catch (Exception e) {
            throw new UnableToPersistException("Medication not saved");
        }
        return medicationModel;
    }

    public List<MedicationModel> getMedicationsFotPet(Long petId) {
        List<Medication> medications = medicationRepository.findByPetId(petId);
        return getModelsFromMedications(medications);
    }

    private List<MedicationModel> getModelsFromMedications(List<Medication> medications) {
        return medications.stream().map(m -> new MedicationModel(m.getId(), m.getName(), m.getStart(), m.getEnd(),
                m.getIsActive(), m.getPet().getId(), m.getDosageInstructions())).collect(Collectors.toList());
    }
}
