package com.example.demo.jpa;

import com.example.demo.entity.DoctorK;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableMongoRepositories
public interface DoctorKRepository extends MongoRepository<DoctorK,Long> {


    DoctorK findBynezam(String nazam);

    List<DoctorK> findAllBynezamNotNull();

    List<DoctorK> findAllBySitesNull();

    List<DoctorK> findAllBySites(String site);
    List <DoctorK> findAllBySitesRegex(@Param("regex") String regex);

}
