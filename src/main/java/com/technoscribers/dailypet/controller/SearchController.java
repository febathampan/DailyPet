package com.technoscribers.dailypet.controller;

import com.technoscribers.dailypet.exceptions.InvalidInfoException;
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

    @GetMapping("/details")
    public ResponseEntity<?> searchDetail(@RequestParam Long serviceId) {
        try {
            return ResponseEntity.ok().body(searchService.searchDetail(serviceId));
        } catch (InvalidInfoException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
