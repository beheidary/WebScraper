package com.example.demo.jpa;

import com.example.demo.entity.CityEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import java.util.List;

public interface CityMongoRepository extends MongoRepository<CityEntity,Long> {

    @Query(value = "{}", fields = "{ 'LatinName' : 1}")
    List<String> findAllByLatinNameNotNull();
}