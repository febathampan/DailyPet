package com.technoscribers.dailypet.service;

import com.technoscribers.dailypet.exceptions.InvalidInfoException;
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
        List<AnnouncementModel> models = results.stream().map(a ->  new AnnouncementModel(a.getId(), a.getTitle(), a.getPost(), a.getPublish(), a.getExpire(), a.getIsActive(), a.getOwner().getId())).collect(Collectors.toList());
        return models;
    }

    public AnnouncementModel editAnnouncement(AnnouncementModel model) throws InvalidInfoException {
        Optional<User> u = userService.findById(model.getUserId());
        if (u.isEmpty()) {
            throw new InvalidInfoException("UserID invalid!");
        }

        Announcement a = new Announcement(model.getTitle(), model.getPost(), model.getPublish(), model.getExpire(), model.getIsActive(), u.get());
        if (model.getId() != null && model.getId() > 0) {
            a.setId(model.getId());
        }
        Announcement result = announcementRepository.save(a);
        model.setId(result.getId());
        return model;
    }

    /**
     * Contains active posts and drafts
     * @param ownerId
     * @return
     */
    public List<AnnouncementModel> getAllAnnouncements(Long ownerId) {
        List<Announcement> results = announcementRepository.findByOwnerId(ownerId);
        List<AnnouncementModel> models = results.stream().map(a ->  new AnnouncementModel(a.getId(), a.getTitle(), a.getPost(), a.getPublish(), a.getExpire(), a.getIsActive(), a.getOwner().getId())).collect(Collectors.toList());
        return models;
    }

    /**
     * Contains only active posts
     * @param ownerId
     * @return
     */
    public List<AnnouncementModel> getAnnouncementsForOwnerByActive(Boolean flag, Long ownerId) {
        List<Announcement> results = announcementRepository.findByIsActiveAndOwnerId(flag, ownerId);
        List<AnnouncementModel> models = results.stream().map(a ->  new AnnouncementModel(a.getId(), a.getTitle(), a.getPost(), a.getPublish(), a.getExpire(), a.getIsActive(), a.getOwner().getId())).collect(Collectors.toList());
        return models;
    }

    public String deleteAnnouncement(Long announcementId) {
         announcementRepository.deleteById(announcementId);
        return "Success";
    }

    public AnnouncementModel getAnnouncementById(Long announcementId) throws InvalidInfoException {
        Optional<Announcement> result = announcementRepository.findById(announcementId);
        if(result.isEmpty()){
            throw new InvalidInfoException("Invalid announcement id");
        }
        Announcement a = result.get();
        AnnouncementModel model = new AnnouncementModel(a.getId(), a.getTitle(), a.getPost(), a.getPublish(), a.getExpire(), a.getIsActive(), a.getOwner().getId());
        return model;
    }
}
