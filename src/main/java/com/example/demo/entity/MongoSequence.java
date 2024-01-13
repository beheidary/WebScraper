package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "sequences")
@TypeAlias("MongoSequence")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MongoSequence {
    @Id
    private String id;
    private Long seq;
}