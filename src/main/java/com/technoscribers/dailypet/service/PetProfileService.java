package com.technoscribers.dailypet.service;

import com.technoscribers.dailypet.exceptions.InvalidInfoException;
import com.technoscribers.dailypet.exceptions.UnableToPersistException;
import com.technoscribers.dailypet.model.*;
import com.technoscribers.dailypet.model.enumeration.NotificationType;
import com.technoscribers.dailypet.model.enumeration.WeightMetrics;
import com.technoscribers.dailypet.repository.BreedRepository;
import com.technoscribers.dailypet.repository.PetDetailsRepository;
import com.technoscribers.dailypet.repository.UserRepository;
import com.technoscribers.dailypet.repository.entity.Breed;
import com.technoscribers.dailypet.repository.entity.PetDetails;
import com.technoscribers.dailypet.repository.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PetProfileService {

    @Autowired
    BreedRepository breedRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PetDetailsRepository petRepository;

    @Autowired
    AppointmentService appointmentService;

    @Autowired
    MedicationService medicationService;

    @Autowired
    VaccineService vaccineService;

    @Autowired
    BreedService breedService;

    @Autowired
    AnnouncementService announcementService;

    public PetDetailsModel savePet(PetDetailsModel model) throws InvalidInfoException, UnableToPersistException {
        //save Pet
        PetDetails details = savePetDetailsFromModel(model);
        //save Image
        model.setId(details.getId());
        return model;
    }

    private PetDetails savePetDetailsFromModel(PetDetailsModel model) throws InvalidInfoException, UnableToPersistException {
        if (model != null) {
            Boolean isEdit =(model.getId()!=null && model.getId()>0)? true :false;
            Optional<Breed> breed = breedRepository.findById(model.getBreedId());
            Optional<User> user = userRepository.findById(model.getOwnerId());
            if (breed.isEmpty()) {
                throw new InvalidInfoException("Unable to find breed with ID: " + model.getBreedId());
            }
            if (user.isEmpty()) {
                throw new InvalidInfoException("Unable to find user with ID: " + model.getOwnerId());
            }
            PetDetails details = new PetDetails(model.getName(), model.getDob(), model.getGender(), model.getIdNo(),
                    model.getWeight(), model.getUnit().name(), breed.get(), user.get(), model.getImageURL());
            if(isEdit){
                details.setId(model.getId());
            }
            PetDetails savedDetails = petRepository.save(details);
            if ((savedDetails != null)&& !isEdit) {

                if (!model.getAppointments().isEmpty()) {
                    appointmentService.saveAppointmentForPet(model.getAppointments(), Optional.of(savedDetails));
                }
                if (!model.getVaccines().isEmpty()) {
                    vaccineService.saveVaccineForPet(model.getVaccines(), Optional.of(savedDetails));
                }
                if (!model.getMedications().isEmpty()) {
                    medicationService.saveMedicationForPet(model.getMedications(), Optional.of(savedDetails));
                }
            } else {
                appointmentService.editAppointmentForPet(model.getAppointments(), Optional.of(savedDetails));
                vaccineService.editVaccineForPet(model.getVaccines(), Optional.of(savedDetails));
                medicationService.editMedicationForPet(model.getMedications(), Optional.of(savedDetails));

            }
            return savedDetails;

        }
        throw new InvalidInfoException("Pet details incomplete!");
    }

    public Optional<PetDetails> findById(Long petId) {
        return petRepository.findById(petId);
    }

    public List<PetDetailsModel> getPetDetailsForUser(User user) {
        List<PetDetails> petDetails = petRepository.findByOwner(user);
        List<PetDetailsModel> petDetailsModels = petDetails.stream().map( p -> {
            PetDetailsModel model=new PetDetailsModel(p.getId(), p.getName(), p.getDob(), p.getGender(),
                    p.getIdNo(), p.getWeight(), WeightMetrics.valueOf(p.getWeightUnit()),p.getBreed().getId(), p.getOwner().getId(), p.getImageURL(), breedService.getModel(p.getBreed()));
             List<AppointmentModel> appointments = appointmentService.getAppointmentsFotPet(p.getId());
             List<VaccineModel> vaccines = vaccineService.getVaccinesFotPet(p.getId());
             List<MedicationModel> medications = medicationService.getMedicationsFotPet(p.getId());
             model.setAppointments(appointments);
             model.setVaccines(vaccines);
             model.setMedications(medications);
             return model;
        }).collect(Collectors.toList());
        return petDetailsModels;
    }

    public Boolean lostPet(Long petId) throws InvalidInfoException {
        Boolean alertActive = false;
        Optional<PetDetails> pet = petRepository.findById(petId);
        if (pet.isEmpty()) {
            throw new InvalidInfoException("Invalid pet details!");
        }
        AnnouncementModel model = createAnnouncementModel(pet.get());
        alertActive = announcementService.saveAnnouncement(model);
        return alertActive;
    }

    private AnnouncementModel createAnnouncementModel(PetDetails petDetails) {
        AnnouncementModel model = new AnnouncementModel();
        model.setTitle("Pet Lost Alert");
        String message = petDetails.getName() + " lost it's owner." +
                "If you find " + petDetails.getName() + " wandering, please email to " + petDetails.getOwner().getEmail() + " . ";
        model.setPost(message);
        model.setUserId(petDetails.getOwner().getId());
        model.setIsActive(Boolean.TRUE);
        model.setPublish(LocalDateTime.now());
        model.setExpire(LocalDateTime.now().plusDays(14));
        model.setCreatedDate(LocalDateTime.now());
        model.setImageURL(petDetails.getImageURL());
        return model;
    }

    //Notifications are just one way text messages.
    public List<NotificationModel> getNotifications(Long ownerId) throws InvalidInfoException {
        Optional<User> owner = userRepository.findById(ownerId);
        if(owner.isEmpty()){
            throw new InvalidInfoException("Invalid details");
        }
        List<PetDetails> pets = petRepository.findByOwner(owner.get());

        // Use Streams to fetch appointments for each pet
        Map<String ,List<AppointmentModel>> appointmentMap = pets.stream()
                .collect(Collectors.toMap(
                        PetDetails::getName, // Key: Pet name
                        pet -> appointmentService.getAppointmentsFotPet(pet.getId()) // Value: List of appointments
                ));

        // Use Streams to fetch vaccines for each pet
        Map<String ,List<VaccineModel>> vaccineMap = pets.stream()
                .collect(Collectors.toMap(
                        PetDetails::getName, // Key: Pet name
                        pet -> vaccineService.getVaccinesFotPet(pet.getId()) // Value: List of appointments
                ));

        // Use Streams to fetch appointments for each pet
        Map<String ,List<MedicationModel>> medicationMap = pets.stream()
                .collect(Collectors.toMap(
                        PetDetails::getName, // Key: Pet name
                        pet -> medicationService.getMedicationsFotPet(pet.getId()) // Value: List of appointments
                ));


        List<NotificationModel> notifications = new ArrayList<>();
        notifications.addAll(Collections.emptyList());
        notifications.addAll(getAppointmentNotifications(appointmentMap));
        notifications.addAll(getVaccineNotifications(vaccineMap));
        notifications.addAll(getMedicationNotifications(medicationMap));
        return notifications;

    }

    private List<NotificationModel> getMedicationNotifications(Map<String, List<MedicationModel>> medicationMap) {
        if (medicationMap.isEmpty()) {
            return Collections.emptyList();
        }
        List<NotificationModel> notifications = new ArrayList<>();

        for (String petName : medicationMap.keySet()) {
            List<MedicationModel> medModels = medicationMap.get(petName);
            List<MedicationModel> alertMedModels = medModels.stream().filter( v -> isTodayIncluded(v.getStart(), v.getEnd())).collect(Collectors.toList());

            List<NotificationModel> nModels = alertMedModels.stream().map(med -> {
                String desc = petName + ": " + med.getName() + "\n" +
                        "Dosage: " + med.getDosageInstructions() + "\n";
String notificationMessage = "It is time to give "+petName +" their "+ med.getName()+
        ". Dosage instruction: "+ med.getDosageInstructions();
                NotificationModel m = new NotificationModel(med.getId(), NotificationType.MEDICATION,
                        notificationMessage, LocalDateTime.now(), null, petName);
                return m;
            }).collect(Collectors.toList());
            notifications.addAll(nModels);
        }
        return notifications;
    }

    private boolean isTodayIncluded(Date start, Date end) {
        if (start == null || end == null) {
            return false; // Null safety check
        }

    // Truncate the start and end dates to remove the time component
        Date today = new Date();
        Date startOfToday = truncateTime(today);
        Date startDate = truncateTime(start);
        Date endDate = truncateTime(end);

    // Check if today's date falls within the start and end range (inclusive)
        return !startOfToday.before(startDate) && !startOfToday.after(endDate);
    }
    // Helper method to truncate time from a Date
    private Date truncateTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    private List<NotificationModel> getVaccineNotifications(Map<String, List<VaccineModel>> vaccineMap) {
        if (vaccineMap.isEmpty()) {
            return Collections.emptyList();
        }
        List<NotificationModel> notifications = new ArrayList<>();

        for (String petName : vaccineMap.keySet()) {
            List<VaccineModel> vaccineModels = vaccineMap.get(petName);
            List<VaccineModel> alertVaccines = vaccineModels.stream().filter( v -> isWithinDays(v.getScheduledDate(),2)).collect(Collectors.toList());
            List<NotificationModel> nModels = alertVaccines.stream().map(vac -> {
                DateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                String aptDate = df.format(vac.getScheduledDate());

                String notificationMsg = "There is a vaccine("+ vac.getName()+") scheduled for "+ petName+ " on "+ aptDate;
                String desc = petName + ": " + "\n" + vac.getName() + "\n" +
                        "Scheduled date: " + aptDate + "\n";
                if (vac.getDescription() != null) {
                    desc = desc + vac.getDescription();
                };
                NotificationModel m = new NotificationModel(vac.getId(), NotificationType.VACCINATION,
                        notificationMsg, LocalDateTime.now(), null, petName);
                return m;
            }).collect(Collectors.toList());
            notifications.addAll(nModels);
        }
        return notifications;
    }

    private boolean isWithinDays(Date date, int noOfDays) {
        if (date == null) {
            return false; // Null-safe check
        }

        // Get the current date and time
        Instant now = Instant.now();

        // Convert the given Date to an Instant
        Instant givenInstant = date.toInstant();


        // Convert the given Instant to LocalDate (ignoring time) and then to start of that day
            LocalDate givenLocalDate = givenInstant.atZone(ZoneOffset.UTC).toLocalDate();
        Instant givenStartOfDay = givenLocalDate.atStartOfDay().toInstant(ZoneOffset.UTC);

        // Calculate the cutoff date as an Instant
        Instant cutoffInstant = now.plusSeconds(noOfDays * 24 * 60 * 60);

        // Check if the given date is today or after, and within the range
        return !givenInstant.isBefore(now) && !givenStartOfDay.isAfter(cutoffInstant);
    }

    private List<NotificationModel> getAppointmentNotifications(Map<String, List<AppointmentModel>> appointments) {
        if (appointments.isEmpty()) {
            return Collections.emptyList();
        }

        List<NotificationModel> notifications = new ArrayList<>();
        for (String petName : appointments.keySet()) {
            List<AppointmentModel> appointmentModels = appointments.get(petName);
            List<AppointmentModel> alertApts = appointmentModels.stream().filter( v -> isWithinDays(v.getDate(),2)).collect(Collectors.toList());
            List<NotificationModel> nModels = alertApts.stream().map(app -> {
                DateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                String aptDate = df.format(app.getDate());
                String desc = petName + ": " + "\n" + app.getTitle() + "\n" +
                        app.getLocation() + "\n" +
                        aptDate + ": " + app.getStartTime() + " - " + app.getEndTime() + "\n";

                String notificationMsg = "You have an appointment - "+ app.getTitle() +" for "+petName+ " on "+ aptDate+ " from "
                        + app.getStartTime()+" to " + app.getEndTime()+" at "+ app.getLocation();
                if(app.getDescription()!=null){
                    desc = desc + app.getDescription();
                };
                NotificationModel m = new NotificationModel(app.getId(), NotificationType.APPOINTMENT,
                        notificationMsg, LocalDateTime.now(), null, petName);
                return m;
            }).collect(Collectors.toList());
            notifications.addAll(nModels);
        }
        return notifications;
    }
}
