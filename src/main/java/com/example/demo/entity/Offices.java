package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Offices implements Serializable {

    private String id;
    private String typeId;
    private String name;
    private String address;
    private String telephone;
    private String fax;
    private Double latitude;
    private Double longitude;
    private String locationImage;
    private CityEntity city;
    private OfficeDoctor officeDoctor;
    private String virtualVacations;
    private String firstFreeTime;
    private Location location;


}
