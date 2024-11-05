package com.technoscribers.dailypet.model;

import com.technoscribers.dailypet.model.enumeration.RoleName;
import com.technoscribers.dailypet.model.enumeration.ServiceType;
import lombok.Data;

import java.util.List;

@Data
public class ServiceDashboardModel {
    private Long userId;
    private Long serviceId;
    private Long personId;
    private String name;
    private RoleName role;
    private ServiceType serviceType;
    private List<AnnouncementModel> posts;
    private DPServiceModel serviceDetails;
    private DPPersonModel petwalkerDetails;
}
