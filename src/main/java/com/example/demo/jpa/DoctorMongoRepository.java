package com.example.demo.jpa;
import com.example.demo.entity.DoctorData;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface DoctorMongoRepository extends MongoRepository<DoctorData, String> {


    //@Query(value = "{}", fields = "{ 'LatinName' : 1}")
    List<String> findAllBycommentCountNotNull();
}
