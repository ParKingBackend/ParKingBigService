package com.example.parkingbigservice.controller;

import com.example.parkingbigservice.model.Client;
import com.example.parkingbigservice.model.Person;
import com.example.parkingbigservice.service.ClientService;
import com.example.parkingbigservice.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/persons")
public class PersonController {
    private final PersonService personService;
    private final ClientService clientService;


    @Autowired
    public PersonController(PersonService personService, ClientService clientService) {
        this.personService = personService;
        this.clientService = clientService;

    }

    @GetMapping("/get/all")
    public List<Person> getAllPersons() {
        return personService.getAllPersons();
    }

    @GetMapping("/get/{id}")
    public Person getPersonById(@PathVariable Long id) {
        return personService.getPersonById(id);
    }

    @PostMapping("/create/{clientId}")
    public ResponseEntity<Object> createPerson(@PathVariable Long clientId, @RequestBody Person person) {
        Optional<Client> optionalClient = Optional.ofNullable(clientService.findById(clientId));
        if (optionalClient.isPresent()) {
            Client client = optionalClient.get();
            person.setClient(client);

            Person createdPerson = personService.createPerson(person);

            return ResponseEntity.ok(createdPerson);
        } else {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Client with ID " + clientId + " not found.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updatePerson(@PathVariable Long id, @RequestBody Person updatedPerson) {
        Person updated = personService.updatePerson(id, updatedPerson);

        if (updated != null) {
            return ResponseEntity.ok(updated);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping("delete/{id}")
    public ResponseEntity<Object> deletePerson(@PathVariable Long id) {
        boolean deleted = personService.deletePerson(id);


        Map<String, String> response = new HashMap<>();
        if (deleted) {
            response.put("message", "Person with ID " + id + " has been deleted.");
            return ResponseEntity.ok(response);
        } else {

            response.put("error", "Person with ID " + id + " not found.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

}
