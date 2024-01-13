package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OfficeDoctor implements Serializable {

    private String status;
    private DaysDetails daysDetails;
    private String role;
    private Integer isRequireFullPayment;
    private Integer isPresence;
    private Integer price;
    private Integer besinaIsActive;
    private Integer appointmentIsPrivate;
    private Boolean hasActivePackage;
}
