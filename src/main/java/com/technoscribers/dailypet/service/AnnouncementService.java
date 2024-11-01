package com.technoscribers.dailypet.service;

import com.technoscribers.dailypet.exceptions.IncompleteInfoException;
import com.technoscribers.dailypet.exceptions.UnableToPersistException;
import com.technoscribers.dailypet.model.AnnouncementModel;
import com.technoscribers.dailypet.repository.AnnouncementRepository;
import com.technoscribers.dailypet.repository.entity.Announcement;
import com.technoscribers.dailypet.repository.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AnnouncementService {

    @Autowired
    AnnouncementRepository announcementRepository;

    @Autowired
    UserService userService;

    public List<AnnouncementModel> getAllAnnouncements() {
        List<Announcement> results = announcementRepository.findByIsActive(Boolean.TRUE);
        List<AnnouncementModel> models = results.stream().map(a ->  new AnnouncementModel(a.getId(), a.getPost(), a.getPosted(), a.getExpire(), a.getIsActive(), a.getOwner().getId())).collect(Collectors.toList());
        return models;
    }

    public AnnouncementModel editAnnouncement(AnnouncementModel model) throws IncompleteInfoException {
        Optional<User> u = userService.findById(model.getUserId());
        if (u.isEmpty()) {
            throw new IncompleteInfoException("UserID invalid!");
        }

        Announcement a = new Announcement(model.getPost(), model.getPosted(), model.getExpire(), model.getIsActive(), u.get());
        if (model.getId() != null && model.getId() > 0) {
            a.setId(model.getId());
        }
        Announcement result = announcementRepository.save(a);
        model.setId(result.getId());
        return model;
    }

    public List<AnnouncementModel> getAllAnnouncements(Long ownerId) {
        List<Announcement> results = announcementRepository.findByIsActiveAndOwnerId(Boolean.TRUE, ownerId);
        List<AnnouncementModel> models = results.stream().map(a ->  new AnnouncementModel(a.getId(), a.getPost(), a.getPosted(), a.getExpire(), a.getIsActive(), a.getOwner().getId())).collect(Collectors.toList());
        return models;
    }

    public String deleteAnnouncement(Long announcementId) {
         announcementRepository.deleteById(announcementId);
        return "Success";
    }
}
