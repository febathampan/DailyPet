package com.technoscribers.dailypet.service;

import com.technoscribers.dailypet.exceptions.InvalidInfoException;
import com.technoscribers.dailypet.model.*;
import com.technoscribers.dailypet.model.enumeration.RoleName;
import com.technoscribers.dailypet.model.enumeration.ServiceType;
import com.technoscribers.dailypet.repository.PetDetailsRepository;
import com.technoscribers.dailypet.repository.entity.DpService;
import com.technoscribers.dailypet.repository.entity.Person;
import com.technoscribers.dailypet.repository.entity.PetDetails;
import com.technoscribers.dailypet.repository.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.desktop.OpenFilesEvent;
import java.util.List;
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

    @Autowired
    PetProfileService petProfileService;


    public UserDashboardModel getDashboardForUser(Long userId) throws InvalidInfoException {
        UserDashboardModel dashboardModel = new UserDashboardModel();
        Optional<User> userResult = userService.findById(userId);
        if (userResult.isEmpty()) {
            throw new InvalidInfoException("Invalid userId");
        }
        User user = userResult.get();
        Optional<Person> personResult = personService.findPersonForUser(user);
        if (personResult.isEmpty()) {
            throw new InvalidInfoException("Invalid person");
        }
        Person person = personResult.get();
        DPPersonModel personModel = personService.getPerson(person);
        List<PetDetailsModel> petDetails = petProfileService.getPetDetailsForUser(user);
        List<AnnouncementModel> announcements = announcementService.getAllActiveAnnouncements();
        dashboardModel.setUserId(userId);
        dashboardModel.setRole(RoleName.valueOf(user.getRoles().getName()));
        dashboardModel.setName(person.getFname());
        dashboardModel.setPetList(petDetails);
        dashboardModel.setAnnouncements(announcements);
        dashboardModel.setProfile(personModel);
        return dashboardModel;
    }

    public ServiceDashboardModel getDashboardForService(Long serviceId) throws InvalidInfoException {
        ServiceDashboardModel dashboardModel = new ServiceDashboardModel();
        Optional<DpService> serviceResult = getServiceFromId(serviceId);
        DpService dpService = serviceResult.get();
        Optional<User> userResult = getUserFromId(dpService.getUser().getId());
        User user = userResult.get();
        dashboardModel.setUserId(dpService.getUser().getId());
        dashboardModel.setName(dpService.getName());
        dashboardModel.setPhone(dpService.getPhone());
        dashboardModel.setAddress(dpService.getAddress());
        dashboardModel.setCity(dpService.getCity());
        dashboardModel.setProvince(dpService.getProvince());
        dashboardModel.setPinCode(dpService.getPinCode());
        dashboardModel.setRole(RoleName.valueOf(user.getRoles().getName()));
        dashboardModel.setServiceType(ServiceType.valueOf(dpService.getType()));
        dashboardModel.setServiceId(dpService.getId());
        if (ServiceType.valueOf(dpService.getType()).equals(ServiceType.PETWALKER)) {
            dashboardModel.setPetwalkerDetails(getPwPersonModel(dpService.getPerson()));
            dashboardModel.setPersonId(dpService.getPerson().getId());
        }
        dashboardModel.setPosts(announcementService.getAllAnnouncements(dpService.getUser().getId()));
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
        if (userResult.isEmpty()) {
            throw new InvalidInfoException("Invalid user details - corrupt DB data");
        }
        return userResult;
    }

    private Optional<DpService> getServiceFromId(Long serviceId) throws InvalidInfoException {
        Optional<DpService> serviceResult = dpServiceService.findById(serviceId);
        if (serviceResult.isEmpty()) {
            throw new InvalidInfoException("Invalid service details");
        }
        return serviceResult;
    }
}
