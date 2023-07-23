package com.spring.clientmanager.service;

import com.spring.clientmanager.exception.UserNotFoundException;
import com.spring.clientmanager.model.Client;
import com.spring.clientmanager.repo.ClientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.sql.SQLIntegrityConstraintViolationException;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
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

    public Client addClient(Client client) throws SQLIntegrityConstraintViolationException {

        Client clientsRecord = new Client(
                client.getId(),
                client.getFirstName(),
                client.getLastName(),
                client.getMobileNumber(),
                client.getPhysicalAddress()
        );

        Client existingClient = clientRepo.findClientById(clientsRecord.getId());
        if (existingClient == null || existingClient.getId() != clientsRecord.getId()) {

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
                System.out.println("isValid: " + idNumberData.isValid());
                throw new RuntimeException();
            }

        } else {
            throw new SQLIntegrityConstraintViolationException(String.valueOf(client.getId()));
        }

        return client;
    }

    public List<Client> findAllClients() {
        return clientRepo.findAll();
    }

    public Client updateClient(Client client, long id) throws Exception {

        Client clientsRecord = new Client();

        try{
            clientsRecord = clientRepo.findClientById(id);
        }catch (Exception e){
            new UserNotFoundException("User does not exist");
        }
        if (Objects.nonNull(client.getFirstName()) && !"".equalsIgnoreCase(client.getFirstName())
                &&  Objects.nonNull(client.getLastName()) && !"".equalsIgnoreCase(client.getLastName())
                &&  Objects.nonNull(client.getMobileNumber()) && !"".equals(client.getMobileNumber())
                &&  Objects.nonNull(client.getPhysicalAddress()) && !"".equalsIgnoreCase(client.getPhysicalAddress())) {

            clientsRecord.setId(client.getId());
            clientsRecord.setFirstName(client.getFirstName());
            clientsRecord.setLastName(client.getLastName());
            clientsRecord.setMobileNumber(client.getMobileNumber());
            clientsRecord.setPhysicalAddress(client.getPhysicalAddress());

        } else {
            throw new Exception();
        }

        return clientRepo.save(clientsRecord);
    }

    public Client findClientById(Long id) {
        return clientRepo.findClientById(id);
               // .orElseThrow(() -> new UserNotFoundException("User by id " + id + " was not found"));
    }

    public List<Client> findClientsByFirstName(String firstName) throws UserNotFoundException {
        List<Client> client = clientRepo.findClientsByFirstName(firstName);
        List<Client> clientReturn = new ArrayList<>();

        if (client != null && !client.isEmpty()){
            clientReturn = clientRepo.findClientsByFirstName(firstName);
        } else {
            throw new UserNotFoundException(firstName + " was not found");
        }
        return clientReturn;
    }

    public Client findClientByMobileNumber(Long mobileNumber){
        return clientRepo.findClientByMobileNumber(mobileNumber);
    }

    public void deleteClient(Long id){
        clientRepo.deleteClientById(id);
    }
}