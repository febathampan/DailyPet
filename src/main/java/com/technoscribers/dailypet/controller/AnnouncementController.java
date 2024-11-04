package com.technoscribers.dailypet.controller;

import com.technoscribers.dailypet.exceptions.InvalidInfoException;
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

    /**
     * Get announcements for Owner
     * @param ownerId
     * @return
     */
    @GetMapping("/owner")
    public ResponseEntity<?> getAllActiveAnnouncements(@RequestParam Long ownerId) {
        List<AnnouncementModel> results = announcementService.getAllAnnouncements(ownerId);
        return ResponseEntity.ok().body(results);
    }

    @PostMapping()
    public ResponseEntity<?> editAnnouncement(@RequestBody AnnouncementModel announcementModel) {
        try {
            return ResponseEntity.ok().body(announcementService.editAnnouncement(announcementModel));
        } catch (InvalidInfoException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping()
    public ResponseEntity<String> deleteAnnouncements(@RequestParam Long announcementId) {
        return ResponseEntity.ok().body(announcementService.deleteAnnouncement(announcementId));
    }

    @GetMapping
    public ResponseEntity<?> getAnnouncement(@RequestParam Long announcementId) {

        AnnouncementModel results = null;
        try {
            results = announcementService.getAnnouncementById(announcementId);
            return ResponseEntity.ok().body(results);
        } catch (InvalidInfoException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }


}
