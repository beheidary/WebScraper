package com.example.demo.dto.InDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CityInDto implements Serializable {

    private String name;
    private String id;
}