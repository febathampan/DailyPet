package com.technoscribers.dailypet.controller;

import com.technoscribers.dailypet.exceptions.InvalidInfoException;
import com.technoscribers.dailypet.model.AddressModel;
import com.technoscribers.dailypet.model.ProfileModel;
import com.technoscribers.dailypet.model.UserModel;
import com.technoscribers.dailypet.service.DpServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/service")
public class DpServicesController {
    @Autowired
    DpServiceService dpServiceService;

    @PostMapping("/profile")
    public ResponseEntity<?> changeProfileDetails(@RequestBody ProfileModel profileModel) {
        try {
            Boolean saved = dpServiceService.saveProfileDetails(profileModel);
            if (saved)
                return ResponseEntity.ok().body("Saved");
            else
                return ResponseEntity.badRequest().body("Not Saved");
        } catch (InvalidInfoException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PostMapping("/address")
    public ResponseEntity<?> changeAddressDetails(@RequestBody AddressModel addressModel) {
        try {
            Boolean saved = dpServiceService.saveAddressDetails(addressModel);
            if (saved)
                return ResponseEntity.ok().body("Saved");
            else
                return ResponseEntity.badRequest().body("Not Saved");
        } catch (InvalidInfoException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
