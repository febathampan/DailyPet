package com.technoscribers.dailypet.controller;

import ch.qos.logback.core.model.AppenderModel;
import com.technoscribers.dailypet.exceptions.UnableToPersistException;
import com.technoscribers.dailypet.model.AppointmentModel;
import com.technoscribers.dailypet.model.VaccineModel;
import com.technoscribers.dailypet.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    @Autowired
    AppointmentService appointmentService;
    @PostMapping()
    public ResponseEntity<?> addAppointment(@RequestBody AppointmentModel model) {
        try {
            AppointmentModel result = appointmentService.saveAppointmentForPet(model);
            return ResponseEntity.ok().body(result);
        } catch (UnableToPersistException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/all")
    public ResponseEntity<?> addAppointment(@RequestBody List<AppointmentModel> models) {
        try {
            List<AppointmentModel> results = appointmentService.saveAppointmentForPet(models);
            return ResponseEntity.ok().body(results);
        } catch (UnableToPersistException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
