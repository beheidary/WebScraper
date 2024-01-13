package com.example.demo.entity;


import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;



@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "city")
public class CityEntity {

    @Id

    private String id;
    @Field("Name")
    private String name;
    @Field("LatinName")
    private String LatinName;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLatinName() {
        return LatinName;
    }

    public void setLatinName(String latinName) {
        LatinName = latinName;
    }



}

