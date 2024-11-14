package com.technoscribers.dailypet.service;

import com.technoscribers.dailypet.exceptions.InvalidInfoException;
import com.technoscribers.dailypet.model.PWAvailabilityModel;
import com.technoscribers.dailypet.model.SearchRequest;
import com.technoscribers.dailypet.model.ServiceSearchDetailModel;
import com.technoscribers.dailypet.model.ServiceSearchModel;
import com.technoscribers.dailypet.model.enumeration.SearchFilter;
import com.technoscribers.dailypet.model.enumeration.ServiceType;
import com.technoscribers.dailypet.repository.DpServiceRepository;
import com.technoscribers.dailypet.repository.entity.DpService;
import com.technoscribers.dailypet.repository.entity.PWAvailability;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SearchService {
    @Autowired
    DpServiceRepository dpServiceRepository;

    @Autowired
    PWService pwService;


    public static Specification<DpService> searchSpec(SearchRequest request) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Apply filters on the main entity (Item)
            if (request.getId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("id"), request.getId()));
            }
            if (request.getFilter() != null && !(request.getFilter().equals(SearchFilter.ALL))) {
                SearchFilter filter = request.getFilter();
                ServiceType type = filter.equals(SearchFilter.CLINIC) ? ServiceType.CLINIC :
                        filter.equals(SearchFilter.GROOMING) ? ServiceType.GROOMING :
                                ServiceType.PETWALKER;
                predicates.add(criteriaBuilder.like(root.get("type"), "%" + type.name() + "%"));
            }
            if (request.getQuery() != null && !request.getQuery().isEmpty()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + request.getQuery().toLowerCase() + "%"));
            }

            // Join with the Category table and filter by category name
          /*  if (categoryName != null && !categoryName.isEmpty()) {
                var categoryJoin = root.join("category", JoinType.INNER);
                predicates.add(criteriaBuilder.like(categoryJoin.get("name"), "%" + categoryName + "%"));
            }*/

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    public List<ServiceSearchModel> search(SearchRequest request) {
        List<DpService> services = dpServiceRepository.findAll(searchSpec(request));
        List<ServiceSearchModel> models = services.stream().map(s -> {
            List<PWAvailabilityModel> availabilityModels = new ArrayList<>();
            if(s.getType().equals(ServiceType.PETWALKER.name())){
                availabilityModels.addAll(pwService.getAvailability(s.getId()));
            }
            ServiceSearchModel  m = new ServiceSearchModel(s.getId(), s.getName(),s.getPhone(), s.getAddress(), s.getImageURL(), s.getType(), availabilityModels);
            return m;
        }).collect(Collectors.toList());
        return models;
    }

    public ServiceSearchDetailModel searchDetail(Long serviceId) throws InvalidInfoException {
        SearchRequest request = new SearchRequest();
        request.setFilter(SearchFilter.ALL);
        request.setId(serviceId);
        List<DpService> services = dpServiceRepository.findAll(searchSpec(request));
       // List<ServiceSearchModel> models = search(request);
        if(services.isEmpty()){
            throw new InvalidInfoException("Invalid search info");
        }
        ServiceSearchModel result = services.stream().map(s -> new ServiceSearchModel(s.getId(), s.getName(),
                s.getPhone(), s.getAddress(), s.getImageURL(), s.getType(), null)).findFirst().get();//since search is with id, there will only be one result

        ServiceSearchDetailModel detailModel = new ServiceSearchDetailModel();
        detailModel.setService(result);
        if(services.getFirst().getType().equals(ServiceType.PETWALKER.name())){
            detailModel.setPwAvailabilities(pwService.getAvailability(serviceId));
        }
        return detailModel;
    }
}
