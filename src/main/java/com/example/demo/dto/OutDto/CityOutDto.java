package com.example.demo.dto.OutDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CityOutDto implements Serializable {

    private String name;
    private String id;
    private String LatinName;
}