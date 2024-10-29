package com.technoscribers.dailypet.service;

import com.technoscribers.dailypet.model.DPServiceModel;
import com.technoscribers.dailypet.model.PetDetailsModel;
import com.technoscribers.dailypet.repository.DpServiceRepository;
import com.technoscribers.dailypet.repository.entity.DpService;
import com.technoscribers.dailypet.repository.entity.PetDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public DpService save(DpService dpService) {
        return dpServiceRepository.save(dpService);
    }


}
