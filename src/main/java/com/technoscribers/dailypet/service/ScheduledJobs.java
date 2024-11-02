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
    @Scheduled(cron = "0 0 0 * * *")
    public void removingExpiredAnnouncements(){
        System.out.println("Running cron");
        List<Announcement> announcements = announcementRepository.findByIsActiveAndExpireLessThan(Boolean.TRUE, LocalDate.now());
        announcements.forEach(a-> a.setIsActive(Boolean.FALSE));
        announcementRepository.saveAll(announcements);
    }
}
