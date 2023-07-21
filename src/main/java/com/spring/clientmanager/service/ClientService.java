package com.spring.clientmanager.service;

import com.spring.clientmanager.exception.UserNotFoundException;
import com.spring.clientmanager.model.Client;
import com.spring.clientmanager.repo.ClientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

@Service
//@Transactional
public class ClientService {
    @Autowired
    private final ClientRepo clientRepo;

    @Autowired
    public ClientService(ClientRepo clientRepo) {
        this.clientRepo = clientRepo;
    }

    public Client addClient(Client client) {

        Client clientsRecord = new Client();

        try {
            clientsRecord = clientRepo.findClientById(client.getId());
            
        } catch (Exception e){
            new UserNotFoundException("User by id " + clientRepo.findClientById(client.getId()).getId() + " does not exist and can be created");
        }

        if (client.getId() != clientsRecord.getId()) {
            String id = String.valueOf(client.getId());
            IDNumberParser idNumberParser = new IDNumberParser();
            IDNumberData idNumberData = null;
            try {
                idNumberData = idNumberParser.parse(id);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            if (idNumberData.isValid()){
                clientRepo.save(client);
                System.out.println("isValid: " + idNumberData.isValid());
            } else {
                System.out.println("ID number is not a valid South african ID number");
            }

        } else {
            Logger logger = Logger.getLogger("User Already Exists.............." + client);
        }

        return client;
    }

    public List<Client> findAllClients() {
        return clientRepo.findAll();
    }

    public Client updateClient(Client client, long id) {

        Client clientsRecord = new Client();

        try{
            clientsRecord = clientRepo.findClientById(id);

        }catch (Exception e){
            new UserNotFoundException("User does not exist");
        }

        if (Objects.nonNull(client.getFirstName()) && !"".equalsIgnoreCase(client.getFirstName())) {
            clientsRecord.setFirstName(client.getFirstName());
        }
        if (Objects.nonNull(client.getLastName()) && !"".equalsIgnoreCase(client.getLastName())) {
            clientsRecord.setLastName(client.getLastName());
        }
        if (Objects.nonNull(client.getMobileNumber()) && !"".equals(client.getMobileNumber())) {
            clientsRecord.setMobileNumber(client.getMobileNumber());
        }
        if (Objects.nonNull(client.getPhysicalAddress()) && !"".equalsIgnoreCase(client.getPhysicalAddress())) {
            clientsRecord.setPhysicalAddress(client.getPhysicalAddress());
        }
        return clientRepo.save(clientsRecord);
    }

    public Client findClientById(Long id) {
        return clientRepo.findClientById(id);
               // .orElseThrow(() -> new UserNotFoundException("User by id " + id + " was not found"));
    }

    public List<Client> findClientByFirstName(String firstName){
        return clientRepo.findClientByFirstName(firstName);
    }

    public Client findClientByMobileNumber(Long mobileNumber){
        return clientRepo.findClientByMobileNumber(mobileNumber);
    }

    public void deleteClient(Long id){
        clientRepo.deleteClientById(id);
    }
}