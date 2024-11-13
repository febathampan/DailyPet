package com.technoscribers.dailypet.service;

import com.technoscribers.dailypet.model.SearchRequest;
import com.technoscribers.dailypet.model.ServiceSearchModel;
import com.technoscribers.dailypet.model.enumeration.SearchFilter;
import com.technoscribers.dailypet.model.enumeration.ServiceType;
import com.technoscribers.dailypet.repository.DpServiceRepository;
import com.technoscribers.dailypet.repository.entity.DpService;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
public class SearchService {
    @Autowired
    DpServiceRepository dpServiceRepository;


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
        List<ServiceSearchModel> models = services.stream().map(s -> new ServiceSearchModel(s.getId(), s.getName(),
                s.getPhone(), s.getAddress(), s.getImageURL(), s.getType())).collect(Collectors.toList());
        return models;
    }
}
