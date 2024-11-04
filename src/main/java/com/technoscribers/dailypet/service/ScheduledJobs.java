package com.technoscribers.dailypet.service;

import com.technoscribers.dailypet.repository.AnnouncementRepository;
import com.technoscribers.dailypet.repository.entity.Announcement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class ScheduledJobs {

    @Autowired
    AnnouncementRepository announcementRepository;
   // @Scheduled(cron = "0 0 0 * * *")
    @Scheduled(cron = "0 0/2 0 * * *")
    public void removingExpiredAnnouncements(){
        System.out.println("Running cron");
        List<Announcement> announcementsToPublish = announcementRepository.findByIsActiveAndExpireLessThan(Boolean.TRUE, LocalDate.now());
        announcementsToPublish.forEach(a-> a.setIsActive(Boolean.FALSE));

        //Activate those with date greater than today
        List<Announcement> announcementsToConceal = announcementRepository.findByIsActiveAndPublishLessThanEqual(Boolean.FALSE, LocalDate.now());
        announcementsToConceal.forEach(a-> a.setIsActive(Boolean.TRUE));

        announcementRepository.saveAll(announcementsToPublish);
        announcementRepository.saveAll(announcementsToConceal);
    }
}
