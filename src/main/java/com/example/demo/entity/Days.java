package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Days implements Serializable {

    private Boolean friday;
    private Boolean monday;
    private Boolean sunday;
    private String endTime;
    private Boolean tuesday;
    private Boolean isActive;
    private Boolean saturday;
    private Boolean thursday;
    private String startTime;
    private Boolean wednesday;
    private Integer publicCapacity;
    private Boolean publicIsActive;
    private Boolean presenceIsActive;
}
