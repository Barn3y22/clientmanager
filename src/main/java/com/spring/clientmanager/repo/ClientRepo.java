package com.spring.clientmanager.repo;

import com.spring.clientmanager.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClientRepo extends JpaRepository<Client, Long> {
    void deleteClientById(Long id);
    Client findClientById(Long id);
    List<Client> findClientByFirstName(String firstName);
    Client findClientByMobileNumber(Long mobileNumber);
}