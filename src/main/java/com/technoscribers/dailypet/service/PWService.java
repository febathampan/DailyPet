package com.technoscribers.dailypet.service;

import com.technoscribers.dailypet.exceptions.InvalidInfoException;
import com.technoscribers.dailypet.model.PWAvailabilityModel;
import com.technoscribers.dailypet.repository.PWAvailabilityRepository;
import com.technoscribers.dailypet.repository.entity.DpService;
import com.technoscribers.dailypet.repository.entity.PWAvailability;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PWService {

    @Autowired
    PWAvailabilityRepository pwAvailabilityRepository;

    @Autowired
    DpServiceService dpServiceService;

    public PWAvailabilityModel createAvailability(PWAvailabilityModel inputModel) throws InvalidInfoException {
        Optional<DpService> service = dpServiceService.findById(inputModel.getServiceId());
        if (service.isEmpty()) {
            throw new InvalidInfoException("Invalid service details");
        }
        PWAvailability entity = new PWAvailability(inputModel.getWeekDay(), inputModel.getStartTime(), inputModel.getEndTime(), inputModel.getPublish(), service.get());
        entity = pwAvailabilityRepository.save(entity);
        inputModel.setId(entity.getId());
        return inputModel;

    }

    public List<PWAvailabilityModel> getAvailability(Long serviceId) {
        List<PWAvailability> availabilities = pwAvailabilityRepository.findByServiceId(serviceId);
        return getModelFromEntities(availabilities);
    }

    private List<PWAvailabilityModel> getModelFromEntities(List<PWAvailability> availabilities) {
        return availabilities.stream().map(a -> new PWAvailabilityModel(a.getId(), a.getWeekDay(), a.getStartTime(),
                a.getEndTime(), a.getPublish(), a.getService().getId())).collect(Collectors.toList());
    }
}
