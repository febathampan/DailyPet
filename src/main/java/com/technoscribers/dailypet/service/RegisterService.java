package com.technoscribers.dailypet.service;

import com.technoscribers.dailypet.model.UserModel;
import com.technoscribers.dailypet.model.enumeration.RoleName;
import com.technoscribers.dailypet.repository.RolesRepository;
import com.technoscribers.dailypet.repository.UserRepository;
import com.technoscribers.dailypet.repository.entity.Roles;
import com.technoscribers.dailypet.repository.entity.User;
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
public class RegisterService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RolesRepository rolesRepository;

    /**
     * Register user
     *
     * @param userModel
     * @return
     */
    public User registerUser(UserModel userModel) {
        User user = getUser(userModel);
        return userRepository.save(user); //JPA in-built query
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
