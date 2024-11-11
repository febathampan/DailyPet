package com.technoscribers.dailypet.model;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PasswordResetModel {
    @NotNull
    private Long userId;
    @NotNull
    private String currentPwd;
    @NotNull
    private String newPwd;
}
