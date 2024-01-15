package com.example.demo.jpa;

import com.example.demo.entity.CityEntity;
import com.example.demo.entity.ExpertiseEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;


import com.example.demo.entity.CityEntity;
        import org.springframework.data.mongodb.repository.MongoRepository;
        import org.springframework.data.mongodb.repository.Query;
        import java.util.List;

public interface ExpertiseMongoRepository extends MongoRepository<ExpertiseEntity,String> {

    @Query(value = "{}", fields = "{ '_id' : 1}")
    List<String> findAllByIdNotNull();
}