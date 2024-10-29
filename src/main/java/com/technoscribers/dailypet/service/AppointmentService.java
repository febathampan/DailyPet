package com.technoscribers.dailypet.service;

import com.technoscribers.dailypet.exceptions.UnableToPersistException;
import com.technoscribers.dailypet.model.AppointmentModel;
import com.technoscribers.dailypet.model.MedicationModel;
import com.technoscribers.dailypet.repository.AppointmentRepository;
import com.technoscribers.dailypet.repository.PetDetailsRepository;
import com.technoscribers.dailypet.repository.entity.Appointment;
import com.technoscribers.dailypet.repository.entity.Medication;
import com.technoscribers.dailypet.repository.entity.PetDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {

    @Autowired
    PetDetailsRepository petDetailsRepository;

    @Autowired
    AppointmentRepository appointmentRepository;

    public AppointmentModel saveAppointmentForPet(AppointmentModel model) throws UnableToPersistException {
        Optional<PetDetails> petDetails = petDetailsRepository.findById(model.getPetId());
        if (petDetails.isEmpty()) {
            throw new UnableToPersistException("PetDetails not found!");
        }

        Appointment appointment = new Appointment(model.getTitle(), model.getLocation(), model.getDate(), model.getDescription(),
                Boolean.TRUE, petDetails.get());

        Appointment app = appointmentRepository.save(appointment);
        try {
            model.setId(app.getId());
        } catch (Exception e) {
            throw new UnableToPersistException("Appointment not saved");
        }
        return model;
    }

    public List<AppointmentModel> saveAppointmentForPet(List<AppointmentModel> models) throws UnableToPersistException {
        List<AppointmentModel> savedModels = new ArrayList<>();
        for (AppointmentModel a : models) {
            AppointmentModel result = saveAppointmentForPet(a);
            savedModels.add(result);
        }
        return savedModels;
    }
}
