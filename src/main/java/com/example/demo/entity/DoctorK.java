package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "DoctorK")
public class DoctorK {


    @Id
    private String id;

    public void setId(String id) {
        this.id = id;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public String toString() {
        return "DoctorK{" +
                "id='" + id + '\'' +
                ", index=" + index +
                ", full_name='" + full_name + '\'' +
                ", active_count=" + active_count +
                ", sites='" + sites + '\'' +
                ", nezam='" + nezam + '\'' +
                '}';
    }

    @Field(name = "id")
    private int index;

    private String full_name;

    private int active_count;

    private String sites;

    @Field(name = "nezam_code")
    private String nezam;



    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public int getActive_count() {
        return active_count;
    }

    public void setActive_count(int active_count) {
        this.active_count = active_count;
    }

    public String getSites() {
        return sites;
    }

    public void setSites(String sites) {
        this.sites = sites;
    }

    public String getNezam() {
        return nezam;
    }

    public void setNezam(String nezam) {
        this.nezam = nezam;
    }
}
