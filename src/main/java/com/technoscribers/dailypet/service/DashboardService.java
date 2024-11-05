package com.technoscribers.dailypet.service;

import com.technoscribers.dailypet.exceptions.InvalidInfoException;
import com.technoscribers.dailypet.model.DPPersonModel;
import com.technoscribers.dailypet.model.DPServiceModel;
import com.technoscribers.dailypet.model.ServiceDashboardModel;
import com.technoscribers.dailypet.model.UserDashboardModel;
import com.technoscribers.dailypet.model.enumeration.RoleName;
import com.technoscribers.dailypet.model.enumeration.ServiceType;
import com.technoscribers.dailypet.repository.entity.DpService;
import com.technoscribers.dailypet.repository.entity.Person;
import com.technoscribers.dailypet.repository.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.desktop.OpenFilesEvent;
import java.util.Optional;

@Service
public class DashboardService {
    @Autowired
    UserService userService;

    @Autowired
    DpServiceService dpServiceService;

    @Autowired
    PersonService personService;

    @Autowired
    AnnouncementService announcementService;


    public UserDashboardModel getDashboardForUser(Long userId) {
        return null;
    }

    public ServiceDashboardModel getDashboardForService(Long serviceId) throws InvalidInfoException {
        ServiceDashboardModel dashboardModel = new ServiceDashboardModel();
        Optional<DpService> serviceResult = getServiceFromId(serviceId);
        DpService dpService = serviceResult.get();
        Optional<User> userResult = getUserFromId(dpService.getUser().getId());
        User user = userResult.get();

        dashboardModel.setName(dpService.getName());
        dashboardModel.setRole(RoleName.valueOf(user.getRoles().getName()));
        dashboardModel.setServiceType(ServiceType.valueOf(dpService.getType()));
        dashboardModel.setServiceId(dpService.getId());
        if(ServiceType.valueOf(dpService.getType()).equals(ServiceType.PETWALKER)){
            dashboardModel.setPetwalkerDetails(getPwPersonModel(dpService.getPerson()));
        }
        dashboardModel.setServiceDetails(getServiceDetailsFromEntity(dpService));
        dashboardModel.setPosts(announcementService.getAllAnnouncements());
        return dashboardModel;
    }

    private DPServiceModel getServiceDetailsFromEntity(DpService dpService) {
       return dpServiceService.getServiceModel(dpService);
    }

    private DPPersonModel getPwPersonModel(Person person) {
        return personService.getPerson(person);
    }

    private Optional<User> getUserFromId(Long userId) throws InvalidInfoException {
        Optional<User> userResult = userService.findById(userId);
        if(userResult.isEmpty()){
            throw new InvalidInfoException("Invalid user details - corrupt DB data");
        }
        return userResult;
    }

    private Optional<DpService> getServiceFromId(Long serviceId) throws InvalidInfoException {
        Optional<DpService> serviceResult = dpServiceService.findById(serviceId);
        if(serviceResult.isEmpty()){
            throw new InvalidInfoException("Invalid service details");
        }
        return serviceResult;
    }
}
