package com.technoscribers.dailypet.service;

import com.technoscribers.dailypet.model.UserModel;
import com.technoscribers.dailypet.model.enumeration.RoleName;
import com.technoscribers.dailypet.repository.RolesRepository;
import com.technoscribers.dailypet.repository.UserRepository;
import com.technoscribers.dailypet.repository.entity.DpService;
import com.technoscribers.dailypet.repository.entity.Person;
import com.technoscribers.dailypet.repository.entity.Roles;
import com.technoscribers.dailypet.repository.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Registration service - User registrations
 *
 */
@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RolesRepository rolesRepository;

    @Autowired
    DpServiceService dpServiceService;

    @Autowired
    PersonService personService;

    /**
     * Register user
     *
     * @param userModel
     * @return
     */
    @Transactional
    public UserModel registerUser(UserModel userModel) {
        User user = getUser(userModel);
        User savedUser = userRepository.save(user); //JPA in-built query
        //UserModel savedModel = new UserModel(user.getEmail(), RoleName.valueOf(savedUser.getRoles().getName()));
        if(savedUser!=null)
            userModel.setUserId(savedUser.getId());
        if(userModel.getRole()==RoleName.SERVICE){
            DpService dpService =  dpServiceService.getService(userModel.getDpServiceModel());
            dpService.setUser(savedUser);
            DpService savedDpS = dpServiceService.save(dpService);
            if(savedDpS!=null)
                userModel.setServiceId(savedDpS.getId());
        }else{
            Person person = personService.getPerson(userModel.getDpPersonModel());
            person.setUser(savedUser);
            Person savedPerson = personService.save(person);
            if(savedPerson!=null)
                userModel.setPersonId(savedPerson.getId());
        }
        return userModel;
    }

    /**
     * get User entity from userModel object
     *
     * @param userModel
     * @return
     */
    private User getUser(UserModel userModel) {

        //password - need to convert to encrypted code
        return new User(userModel.getEmail(), userModel.getPassword(), Boolean.TRUE, Instant.now(), Instant.now(), getRole(userModel.getRole()));
    }

    private Roles getRole(RoleName role) {
        return rolesRepository.findByName(role.name());
    }

    public List<UserModel> getAllUsers() {
        List<User> results = userRepository.findAll();
        List<UserModel> models = getUserModelFromUser(results);
        return models;
    }

    private List<UserModel> getUserModelFromUser(List<User> results) {
       return results.stream().map( r->
                new UserModel(r.getEmail(), RoleName.valueOf(r.getRoles().getName()))).collect(Collectors.toList());
    }
}
