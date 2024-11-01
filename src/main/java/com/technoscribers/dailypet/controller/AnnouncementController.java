package com.technoscribers.dailypet.controller;

import com.technoscribers.dailypet.exceptions.IncompleteInfoException;
import com.technoscribers.dailypet.model.AnnouncementModel;
import com.technoscribers.dailypet.service.AnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/post")
public class AnnouncementController {

    @Autowired
    AnnouncementService announcementService;

    @GetMapping("/all")
    public ResponseEntity<?> getAllActiveAnnouncements() {
        List<AnnouncementModel> results = announcementService.getAllAnnouncements();
        return ResponseEntity.ok().body(results);
    }
    @GetMapping()
    public ResponseEntity<?> getAllActiveAnnouncements(@RequestParam Long ownerId) {
        List<AnnouncementModel> results = announcementService.getAllAnnouncements(ownerId);
        return ResponseEntity.ok().body(results);
    }

    @PostMapping()
    public ResponseEntity<?> editAnnouncement(@RequestBody AnnouncementModel announcementModel) {
        try {
            return ResponseEntity.ok().body(announcementService.editAnnouncement(announcementModel));
        } catch (IncompleteInfoException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
