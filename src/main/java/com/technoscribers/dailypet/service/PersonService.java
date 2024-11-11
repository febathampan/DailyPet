package com.technoscribers.dailypet.service;

import com.technoscribers.dailypet.model.DPPersonModel;
import com.technoscribers.dailypet.model.enumeration.Sex;
import com.technoscribers.dailypet.repository.PersonRepository;
import com.technoscribers.dailypet.repository.entity.DpService;
import com.technoscribers.dailypet.repository.entity.Person;
import com.technoscribers.dailypet.repository.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class PersonService {

    @Autowired
    PersonRepository personRepository;

    public Person getPerson(DPPersonModel model) {
        Person person = null;
        if (model != null) {
            person = new Person(model.getFName(), model.getLName(), model.getPhone(), model.getGender().name(),
                    model.getAddress(), model.getCity(), model.getProvince(), model.getPincode(), model.getDob(), model.getImageURL());
        }
        return person;
    }

    public DPPersonModel getPerson(Person person) {
        DPPersonModel model = null;
        if (person != null) {
            model = new DPPersonModel(person.getId(), person.getFname(), person.getLname(), person.getPhone(), Sex.valueOf(person.getGender()),
                    person.getAddress(), person.getCity(),person.getProvince(), person.getPincode(), person.getDob(),person.getImageURL());
        }
        return model;
    }

    public Person save(Person person) {
        return personRepository.save(person);
    }

    public Optional<Person> findPersonForUser(User user) {
        return personRepository.findByUser(user);
    }

    public Optional<Person> getPerson(Long personId) {
        return personRepository.findById(personId);
    }
}
