package com.example.parkingbigservice.service;

import com.example.parkingbigservice.model.Person;
import com.example.parkingbigservice.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }

    public Person getPersonById(Long id) {
        return (Person) personRepository.findById(id).orElse(null);
    }

    public Person createPerson(Person person) {
        return personRepository.save(person);
    }

    public Person updatePerson(Long id, Person updatedPerson) {
        // Find the existing person by ID
        Optional<Person> existingPerson = personRepository.findById(id);

        if (existingPerson.isPresent()) {
            // Update the fields of the existing person with the values from the updatedPerson
            Person personToUpdate = existingPerson.get();
            personToUpdate.setFirstName(updatedPerson.getFirstName());
            personToUpdate.setSurname(updatedPerson.getSurname());

            // Save the updated person
            return personRepository.save(personToUpdate);
        } else {
            // Person with the specified ID not found, return null or throw an exception
            return null; // You can also throw an exception if you prefer
        }
    }

    public boolean deletePerson(Long id) {
        if (personRepository.existsById(id)) {
            personRepository.deleteById(id);
            return true; // Deletion was successful
        } else {
            return false; // Person with the specified ID not found
        }
    }

}
