package com.technoscribers.dailypet.model;

import com.technoscribers.dailypet.model.enumeration.WeightMetrics;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Blob;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class PetDetailsModel {
    private Long id;
    private String name;
    private Date dob;
    private String gender;
    private String idNo;
    private Float weight;
    private WeightMetrics unit;
    private Long breedId;
    private Long ownerId;
    //private MultipartFile image; //Is this possible?
    private List<AppointmentModel> appointments;
    private List<VaccineModel> vaccines;
    private List<MedicationModel> medications;
}
