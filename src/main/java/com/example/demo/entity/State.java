package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class State implements Serializable {
    private String name;
    private String slug;
    private Double latitude;
    private Double longitude;
}
