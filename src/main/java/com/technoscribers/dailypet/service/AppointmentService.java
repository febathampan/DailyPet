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
import java.util.stream.Collectors;

@Service
public class AppointmentService {

    @Autowired
    PetDetailsRepository petDetailsRepository;

    @Autowired
    AppointmentRepository appointmentRepository;

    public AppointmentModel saveAppointmentForPet(AppointmentModel model, Optional<PetDetails> petDetails) throws UnableToPersistException {
        if(petDetails.isEmpty()) {
            //Assuming petDetails id is in model
           petDetails = petDetailsRepository.findById(model.getPetId());
        }
        if (petDetails.isEmpty()) {
            throw new UnableToPersistException("PetDetails not found!");
        }

        Appointment appointment = new Appointment(model.getTitle(), model.getLocation(), model.getDate(), model.getDescription(),
                Boolean.TRUE, petDetails.get(), model.getStartTime(), model.getEndTime());

        Appointment app = appointmentRepository.save(appointment);
        try {
            model.setId(app.getId());
        } catch (Exception e) {
            throw new UnableToPersistException("Appointment not saved");
        }
        return model;
    }

    public List<AppointmentModel> saveAppointmentForPet(List<AppointmentModel> models, Optional<PetDetails> petDetails) throws UnableToPersistException {
        List<AppointmentModel> savedModels = new ArrayList<>();
        for (AppointmentModel a : models) {
            AppointmentModel result = saveAppointmentForPet(a,petDetails);
            savedModels.add(result);
        }
        return savedModels;
    }


    public List<AppointmentModel> editAppointmentForPet(List<AppointmentModel> models, Optional<PetDetails> petDetails) throws UnableToPersistException {
        //Delete previous appointments
        List<Appointment> prevAppointments = appointmentRepository.findByPetIdAndIsActiveIsTrue(petDetails.get().getId());
        appointmentRepository.deleteAll(prevAppointments);

        //Save appointments
        return saveAppointmentForPet(models, petDetails);
    }
    
    public List<AppointmentModel> getAppointmentModels(List<Appointment> appointments){
        return appointments.stream().map( a-> new AppointmentModel(a.getId(), a.getTitle(), a.getLocation(), a.getDate(), a.getDescription(), a.getIsActive(), a.getPet().getId(), a.getStartTime(), a.getEndTime())).collect(Collectors.toList());
    }


    public List<AppointmentModel> getAppointmentsFotPet(Long petId) {
        List<Appointment> appointments = appointmentRepository.findByPetId(petId);
        return getAppointmentModels(appointments);
    }
}
