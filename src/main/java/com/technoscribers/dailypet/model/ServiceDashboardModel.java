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
    private String phone;
    private String address;
    private String city;
    private String province;
    private String pinCode;
    private ServiceType serviceType;
    private String imageURL;
    private List<AnnouncementModel> posts;
    private DPPersonModel petwalkerDetails;
}
