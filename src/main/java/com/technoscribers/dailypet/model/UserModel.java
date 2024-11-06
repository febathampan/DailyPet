package com.technoscribers.dailypet.model;

import com.technoscribers.dailypet.model.enumeration.RoleName;
import com.technoscribers.dailypet.model.enumeration.ServiceType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * User Model - Model object to collect data from application
 * RoleName - Enum
 */
@Data
public class UserModel {
    @NotNull
    private String email;
    @NotNull
    private String password;
    private RoleName role;
    private ServiceType serviceType;
    private DPServiceModel dpServiceModel;
    private DPPersonModel dpPersonModel;
    private Long serviceId;
    private Long personId;
    private Long userId;

    public UserModel(Long userId, String email, RoleName role) {
        this.userId = userId;
        this.email = email;
        this.role = role;
    }
}
