package com.example.demo;

import com.example.demo.controller.DoctorController;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
public class DemoApplication {


	public static void main(String[] args) throws JsonProcessingException, InterruptedException {

		ApplicationContext applicationContext = SpringApplication.run(DemoApplication.class, args);
		DoctorController myController = applicationContext.getBean(DoctorController.class);
		myController.innerUpdate();
	}

}
