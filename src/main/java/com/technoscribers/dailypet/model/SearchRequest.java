package com.technoscribers.dailypet.model;

import com.technoscribers.dailypet.model.enumeration.SearchFilter;
import com.technoscribers.dailypet.model.enumeration.ServiceType;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SearchRequest {
    private Long id;
    private String query;
    //private ServiceType serviceType;
    @NotEmpty
    private SearchFilter filter;
}
