package com.spring.clientmanager.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

@Entity
@Table(name = "client", uniqueConstraints = @UniqueConstraint(name = "unique_id_mobile_number", columnNames = { "id" , "mobile_number" }))
public class Client implements Serializable {
    private static final long serialversionUID = 129348938L;

    @Id
    @Column(name = "id")
    private long id;
    @NotBlank(message = "Required field, please include")
    private String firstName;
    @NotBlank(message = "Required field, please include")
    private String lastName;
    @Column(name = "mobile_number")
    private long mobileNumber;
    private String physicalAddress;

    public Client(){}
    public Client(long id, String firstName, String lastName, long mobileNumber, String physicalAddress) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.mobileNumber = mobileNumber;
        this.physicalAddress = physicalAddress;
    }

    public long getId() {
        return id;
    }

    public void setId(long idNumber) {
        this.id = idNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public long getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(long mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getPhysicalAddress() {
        return physicalAddress;
    }

    public void setPhysicalAddress(String physicalAddress) {
        this.physicalAddress = physicalAddress;
    }


    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", physicalAddress='" + physicalAddress + '\'' +
                '}';
    }

}