package com.technoscribers.dailypet.model;

import com.technoscribers.dailypet.model.enumeration.RoleName;
import lombok.Data;

/**
 * User Model - Model object to collect data from application
 * RoleName - Enum
 */
@Data
public class UserModel {
    private String email;
    private String password;
    private RoleName role;

    public UserModel(String email, RoleName role) {
        this.email = email;
        this.role = role;
    }
}
