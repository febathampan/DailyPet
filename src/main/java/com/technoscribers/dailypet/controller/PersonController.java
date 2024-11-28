package com.technoscribers.dailypet.controller;

import com.technoscribers.dailypet.exceptions.InvalidInfoException;
import com.technoscribers.dailypet.model.UserAddressModel;
import com.technoscribers.dailypet.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class PersonController {

    @Autowired
    PersonService personService;
    @PostMapping("/address")
    public ResponseEntity<?> changeAddressDetails(@RequestBody UserAddressModel addressModel) {
        try {
            Boolean saved = personService.saveAddressDetails(addressModel);
            if (saved)
                return ResponseEntity.ok().body("Saved");
            else
                return ResponseEntity.badRequest().body("Not Saved");
        } catch (InvalidInfoException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    //Change profile
}
