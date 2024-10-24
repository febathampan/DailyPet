package com.technoscribers.dailypet.controller;

import com.technoscribers.dailypet.model.UserModel;
import com.technoscribers.dailypet.repository.entity.User;
import com.technoscribers.dailypet.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/home")
public class HomeController {

    @Autowired
    RegisterService registerService;

    /**
     * Health check API
     * Stactus check if server is live
     *
     * HTTP 200 - Success response
     * @return
     */
    @GetMapping("/health")
    public ResponseEntity<String> healthCheck(){
        return ResponseEntity.ok().body("Success!!");
    }

    /**
     * Register user
     * @param userModel
     * @return
     *
     * HTTP 200 - Success Responses
     * HTTP 400 - Error Responses
     */
    @PostMapping("/user")
    public ResponseEntity<String> registerUser(@RequestBody UserModel userModel){
        User user = registerService.registerUser(userModel);
        if(user!=null) {
            return ResponseEntity.ok().body("Saved user!");
        }
        return ResponseEntity.badRequest().body("User failed to register!");

    }

    @GetMapping("/user")
    public ResponseEntity<List<UserModel>> getAllUsers(){
        return ResponseEntity.ok().body(registerService.getAllUsers());
    }
}
