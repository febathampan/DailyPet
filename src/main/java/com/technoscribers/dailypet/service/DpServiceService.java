package com.technoscribers.dailypet.service;

import com.technoscribers.dailypet.model.DPServiceModel;
import com.technoscribers.dailypet.model.PetDetailsModel;
import com.technoscribers.dailypet.model.enumeration.ServiceType;
import com.technoscribers.dailypet.repository.DpServiceRepository;
import com.technoscribers.dailypet.repository.entity.DpService;
import com.technoscribers.dailypet.repository.entity.PetDetails;
import com.technoscribers.dailypet.repository.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DpServiceService {

    @Autowired
    DpServiceRepository dpServiceRepository;

    public DpService getService(DPServiceModel dpServiceModel) {
        DpService dpService = null;
        if (dpServiceModel != null) {
            dpService = new DpService(dpServiceModel.getName(), dpServiceModel.getPhone(),
                    dpServiceModel.getType().name(), dpServiceModel.getAddress(), dpServiceModel.getCity(),
                    dpServiceModel.getProvince(), dpServiceModel.getPinCode());
        }
        return dpService;
    }

    public DPServiceModel getServiceModel(DpService dpService) {
        DPServiceModel dpServiceModel = null;
        if (dpService != null) {
            dpServiceModel = new DPServiceModel(dpService.getName(), dpService.getPhone(),
                    ServiceType.valueOf(dpService.getType()), dpService.getAddress(), dpService.getCity(),
                    dpService.getProvince(), dpService.getPinCode());
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
}
