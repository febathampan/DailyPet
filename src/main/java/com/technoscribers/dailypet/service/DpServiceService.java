package com.technoscribers.dailypet.service;

import com.technoscribers.dailypet.exceptions.InvalidInfoException;
import com.technoscribers.dailypet.model.AddressModel;
import com.technoscribers.dailypet.model.DPServiceModel;
import com.technoscribers.dailypet.model.PetDetailsModel;
import com.technoscribers.dailypet.model.ProfileModel;
import com.technoscribers.dailypet.model.enumeration.ServiceType;
import com.technoscribers.dailypet.repository.DpServiceRepository;
import com.technoscribers.dailypet.repository.entity.DpService;
import com.technoscribers.dailypet.repository.entity.Person;
import com.technoscribers.dailypet.repository.entity.PetDetails;
import com.technoscribers.dailypet.repository.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DpServiceService {

    @Autowired
    DpServiceRepository dpServiceRepository;

    @Autowired
    PersonService personService;

    public DpService getService(DPServiceModel dpServiceModel) {
        DpService dpService = null;
        if (dpServiceModel != null) {
            dpService = new DpService(dpServiceModel.getName(), dpServiceModel.getPhone(),
                    dpServiceModel.getType().name(), dpServiceModel.getAddress(), dpServiceModel.getCity(),
                    dpServiceModel.getProvince(), dpServiceModel.getPinCode(), dpServiceModel.getImageURL());
        }
        return dpService;
    }

    public DPServiceModel getServiceModel(DpService dpService) {
        DPServiceModel dpServiceModel = null;
        if (dpService != null) {
            dpServiceModel = new DPServiceModel(dpService.getName(), dpService.getPhone(),
                    ServiceType.valueOf(dpService.getType()), dpService.getAddress(), dpService.getCity(),
                    dpService.getProvince(), dpService.getPinCode(), dpService.getImageURL());
        }
        return dpServiceModel;
    }

    public DpService save(DpService dpService) {
        return dpServiceRepository.save(dpService);
    }


    public ServiceType getServiceTypeForUser(User user) {
        DpService service = dpServiceRepository.findByUser(user);
        return ServiceType.valueOf(service.getType());
    }
    public DpService getServiceForUser(User user) {
        DpService service = dpServiceRepository.findByUser(user);
        return service;
    }

    public Optional<DpService> findById(Long serviceId) {
        return dpServiceRepository.findById(serviceId);
    }

    @Transactional
    public Boolean saveProfileDetails(ProfileModel profileModel) throws InvalidInfoException {
        Optional<DpService> optService = dpServiceRepository.findById(profileModel.getServiceId());
        if(optService.isEmpty()){
            throw new InvalidInfoException("Invalid service details");
        }
        DpService service = optService.get();
        service.setPhone(profileModel.getPhone());
        service.setName(profileModel.getName());
        service.setImageURL(profileModel.getImageURL());
        if(ServiceType.valueOf(service.getType()).equals(ServiceType.PETWALKER)){
            Optional<Person> optPerson = personService.getPerson(profileModel.getPwPersonId());
            if(optPerson.isEmpty()){
                throw new InvalidInfoException("Invalid person details - DB corrupt");
            }
            Person person = optPerson.get();
            person.setPhone(profileModel.getPhone());
            person.setFname(profileModel.getName());
            person.setLname(profileModel.getLName());
            person.setImageURL(person.getImageURL());
            personService.save(person);
        }
        dpServiceRepository.save(service);
        return Boolean.TRUE;
    }

    public Boolean saveAddressDetails(AddressModel addressModel) throws InvalidInfoException {
        Optional<DpService> optService = dpServiceRepository.findById(addressModel.getServiceId());
        if(optService.isEmpty()){
            throw new InvalidInfoException("Invalid service details");
        }
        DpService service = optService.get();
        service.setAddress(addressModel.getAddress());
        service.setCity(addressModel.getCity());
        service.setProvince(addressModel.getProvince());
        service.setPinCode(addressModel.getPinCode());
        if(ServiceType.valueOf(service.getType()).equals(ServiceType.PETWALKER)){
            Optional<Person> optPerson = personService.getPerson(addressModel.getPwPersonId());
            if(optPerson.isEmpty()){
                throw new InvalidInfoException("Invalid person details - DB corrupt");
            }
            Person person = optPerson.get();
            person.setAddress(addressModel.getAddress());
            person.setProvince(addressModel.getProvince());
            person.setCity(addressModel.getCity());
            person.setPincode(addressModel.getPinCode());
            personService.save(person);
        }
        dpServiceRepository.save(service);
        return Boolean.TRUE;
    }
}
