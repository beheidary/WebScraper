package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoctorData implements Serializable {

    private String id;
    private String firstName;
    private String lastName;
    private String avatar;
    private String bio;
    private String aboutMe;
    private String instagramAccount;
    private String linkedinAccount;
    private Integer isActive;
    private String nezamCode;
    private Integer gender;
    private String persianFirstName;
    private String persianLastName;
    private String persianFullname;
    private String englishFullname;
    private String firstFreeTime;
    private String specialityId;
    private Speciality speciality;
    private String type;
    private Integer rate;
    private Integer rateCount;
    private Integer rateSum;
    private String waitTime;
    private String commentCount;
    private String unreadTicketsCount;
    private Offices offices;
    private String messages;
    private String subscriptionExpiredAt;
    private String subscription;
    private String virtualVisitStatus;
    private Integer hasVirtualVisit;
    private String virtualVisitData;
    private String publicViewable;
    private String createdAt;
    private String seoFirstName;
    private String seoLastName;
    private String seoId;
}