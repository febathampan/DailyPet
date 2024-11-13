package com.technoscribers.dailypet.controller;

import com.technoscribers.dailypet.model.PWAvailabilityModel;
import com.technoscribers.dailypet.model.SearchRequest;
import com.technoscribers.dailypet.model.ServiceSearchModel;
import com.technoscribers.dailypet.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dailypet/search")
public class SearchController {

    @Autowired
    SearchService searchService;

    @PostMapping()
    public ResponseEntity<List<ServiceSearchModel>> search(@RequestBody SearchRequest request) {
        return ResponseEntity.ok().body(searchService.search(request));
    }
}
