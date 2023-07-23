package com.spring.clientmanager.controller;

import com.spring.clientmanager.exception.UserNotFoundException;
import com.spring.clientmanager.model.Client;
import com.spring.clientmanager.service.ClientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

@RestController
@RequestMapping("/client")
public class ClientController {
    @Autowired
    private final ClientService clientService;

    public ClientController(ClientService clientService) {

        this.clientService = clientService;
    }

    @GetMapping("/find/all")
    public ResponseEntity<List<Client>> getAllClients () {
        List<Client> clients = clientService.findAllClients();
        return new ResponseEntity<>(clients, HttpStatus.OK);
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<Client> getClientById (@PathVariable("id") Long id) {
        Client client = clientService.findClientById(id);
        return new ResponseEntity<>(client, HttpStatus.OK);
    }

    @GetMapping("/findByFirstName/{firstName}")
    public ResponseEntity<List<Client>> getClientsByFirstName (@PathVariable ("firstName") String firstName) throws UserNotFoundException {
        List<Client> client = clientService.findClientsByFirstName(firstName);
        return new ResponseEntity<>(client, HttpStatus.OK);
    }

    @GetMapping("/findByMobileNumber/{mobileNumber}")
    public ResponseEntity<Client> getClientByMobileNumber (@PathVariable("mobileNumber") Long mobileNumber) {
        Client client = clientService.findClientByMobileNumber(mobileNumber);
        return new ResponseEntity<>(client, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Client> addClient(@RequestBody @Valid Client client) throws SQLIntegrityConstraintViolationException {
        Client newClient = clientService.addClient(client);
        return new ResponseEntity<>(newClient, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Client> updateClient(@RequestBody Client Client, @PathVariable("id") Long id) throws Exception {
        Client updateClient = clientService.updateClient(Client, id);
        return new ResponseEntity<>(updateClient, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteClient(@PathVariable("id") Long id) {
        clientService.deleteClient(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}