package com.technoscribers.dailypet.model;

import com.technoscribers.dailypet.model.enumeration.WeightMetrics;
import com.technoscribers.dailypet.repository.entity.Breed;
import lombok.AllArgsConstructor;
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
    private String imageURL;
    private BreedModel breedDetails;
    private List<AppointmentModel> appointments;
    private List<VaccineModel> vaccines;
    private List<MedicationModel> medications;

    public PetDetailsModel(Long id, String name, Date dob, String gender, String idNo, Float weight, WeightMetrics unit, Long breedId, Long ownerId, String imageURL, BreedModel breedModel) {
        this.id = id;
        this.name = name;
        this.dob = dob;
        this.gender = gender;
        this.idNo = idNo;
        this.weight = weight;
        this.unit = unit;
        this.breedId = breedId;
        this.ownerId = ownerId;
        this.imageURL = imageURL;
        this.breedDetails = breedModel;
    }
}
