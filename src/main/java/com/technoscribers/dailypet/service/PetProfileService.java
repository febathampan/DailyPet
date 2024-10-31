package com.technoscribers.dailypet.service;

import com.technoscribers.dailypet.exceptions.IncompleteInfoException;
import com.technoscribers.dailypet.exceptions.UnableToPersistException;
import com.technoscribers.dailypet.model.PetDetailsModel;
import com.technoscribers.dailypet.repository.BreedRepository;
import com.technoscribers.dailypet.repository.PetDetailsRepository;
import com.technoscribers.dailypet.repository.UserRepository;
import com.technoscribers.dailypet.repository.entity.Breed;
import com.technoscribers.dailypet.repository.entity.PetDetails;
import com.technoscribers.dailypet.repository.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Optional;

@Service
public class PetProfileService {

    @Autowired
    BreedRepository breedRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PetDetailsRepository petRepository;

    @Autowired
    ImageService imageService;

    @Autowired
    AppointmentService appointmentService;

    @Autowired
    MedicationService medicationService;

    @Autowired
    VaccineService vaccineService;

    public PetDetailsModel savePet(PetDetailsModel model, MultipartFile image) throws IncompleteInfoException, UnableToPersistException {
        //save Pet
        PetDetails details = savePetDetailsFromModel(model, image);
        //save Image
        model.setId(details.getId());
        return model;
    }

    private PetDetails savePetDetailsFromModel(PetDetailsModel model, MultipartFile image) throws IncompleteInfoException, UnableToPersistException {
        if (model != null) {
            Optional<Breed> breed = breedRepository.findById(model.getBreedId());
            Optional<User> user = userRepository.findById(model.getOwnerId());
            if (breed.isEmpty()) {
                throw new IncompleteInfoException("Unable to find breed with ID: " + model.getBreedId());
            }
            if (user.isEmpty()) {
                throw new IncompleteInfoException("Unable to find user with ID: " + model.getOwnerId());
            }
            PetDetails details = new PetDetails(model.getName(), model.getDob(), model.getGender(), model.getIdNo(),
                    model.getWeight(), model.getUnit().name(), breed.get(), user.get());
            PetDetails savedDetails = petRepository.save(details);
            if (savedDetails != null) {
                if (image != null) {
                    try {
                        byte[] fileBytes = image.getBytes();
                        Blob imgBlob = new SerialBlob(fileBytes);
                        imageService.saveImage(imgBlob, savedDetails);
                    } catch (IOException e) {
                        throw new UnableToPersistException("Unable to save pet details" + e.getMessage());

                    } catch (SQLException e) {
                        throw new UnableToPersistException("Unable to save pet details" + e.getMessage());
                    }
                }

                if (!model.getAppointments().isEmpty()) {
                    appointmentService.saveAppointmentForPet(model.getAppointments(), Optional.of(savedDetails));
                }
                if (!model.getVaccines().isEmpty()) {
                    vaccineService.saveVaccineForPet(model.getVaccines(), Optional.of(savedDetails));
                }
                if (!model.getMedications().isEmpty()) {
                    medicationService.saveMedicationForPet(model.getMedications(), Optional.of(savedDetails));
                }
            }
        }
        throw new IncompleteInfoException("Pet details incomplete!");
    }

    public Optional<PetDetails> findById(Long petId) {
        return petRepository.findById(petId);
    }
}