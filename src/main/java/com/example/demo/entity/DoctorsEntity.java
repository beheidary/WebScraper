package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "doctors")
public class DoctorsEntity {

    @Id
    private Long id;

    @Field("DoctorData")
    @Indexed
    private List<DoctorData> data;

    @Field("SaveDateTime")
    private LocalDateTime saveDateTime;

    @Field("SiteName")
    private String siteName;


}
