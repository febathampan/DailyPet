package com.technoscribers.dailypet.model;

import com.technoscribers.dailypet.model.enumeration.RoleName;
import lombok.Data;

import java.util.List;

@Data
public class UserDashboardModel {
   private Long userId;
   private String name;
   private RoleName role;
   private List<PetDetailsModel> petList;
   private List<AnnouncementModel> announcements;
   private DPPersonModel profile;
}
