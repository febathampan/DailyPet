package com.technoscribers.dailypet.controller;

import com.technoscribers.dailypet.exceptions.InvalidInfoException;
import com.technoscribers.dailypet.model.ServiceDashboardModel;
import com.technoscribers.dailypet.model.UserDashboardModel;
import com.technoscribers.dailypet.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping()
public class DashboardController {

    @Autowired
    DashboardService dashboardService;

    @GetMapping("/user/dashboard")
    public ResponseEntity<UserDashboardModel> getUserDashboard(@RequestParam Long userId) {
        UserDashboardModel dash = dashboardService.getDashboardForUser(userId);
        return ResponseEntity.ok().body(dash);
    }
    @GetMapping("/service/dashboard")
    public ResponseEntity<?> getServiceDashboard(@RequestParam Long serviceId) {
        ServiceDashboardModel dash = null;
        try {
            dash = dashboardService.getDashboardForService(serviceId);
            return ResponseEntity.ok().body(dash);
        } catch (InvalidInfoException e) {
            return ResponseEntity.badRequest().body("Invalid info:"+ e.getLocalizedMessage());

        }
    }

}
