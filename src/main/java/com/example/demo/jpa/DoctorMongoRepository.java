package com.example.demo.jpa;

import com.example.demo.entity.DoctorsEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

public interface DoctorMongoRepository extends MongoRepository<DoctorsEntity, Long> {
}
