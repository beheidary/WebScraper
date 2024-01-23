package com.example.demo.service;

import com.example.demo.dto.DoctorOutputDto;
import com.example.demo.jpa.ExpertiseMongoRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mongodb.client.MongoClient;
import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.time.LocalDateTime;
import java.util.Objects;


@Service
@RequiredArgsConstructor
public class SnappAppointmentService {


    private final ExpertiseMongoRepository expertiseMongoRepository;

    private final ObjectMapper objectMapper;
    private final MongoClient mongoClient;
    private final String apiUrl = "https://snapp.doctor/webapp/appointment/expertsByCity/";
    private final String[] cities = {"yazd","karaj","shiraz","mashhad","tehran","isfahan"};



    public DoctorOutputDto GetSnappAppointmentdoctors() throws JsonProcessingException, InterruptedException {


        HttpHeaders headers = new HttpHeaders();
        headers.set("accept", "application/json");
        headers.set("Content-Type","application/json");
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("accept-language", "en-GB,en;q=0.5");
        headers.set("origin", "https://snapp.doctor");
        headers.set("referer", "https://snapp.doctor/");
        headers.set("sec-fetch-dest", "empty");
        headers.set("sec-fetch-mode", "cors");



        for (String city : cities) {

            JsonNode jsonNode;
            String scrollId = "";

            boolean isFirstTime = true;
            ResponseEntity<String> responseEntity;

            do {

                if (isFirstTime) {

                    String apiUrl = String.format("https://api.snapp.doctor/search/reservation/list?sort[0]=insurance:desc&sort[1]=rate:desc&city=%s", city);
                    HttpEntity<String> requestEntity = new HttpEntity<>(headers);
                    responseEntity = new RestTemplate().exchange(apiUrl, HttpMethod.GET, requestEntity, String.class);
                    String jsonPart = responseEntity.getBody();

                    jsonNode = objectMapper.readTree(jsonPart);

                } else {


                    String apiUrl = String.format("https://api.snapp.doctor/search/reservation/list?sort[0]=insurance:desc&sort[1]=rate:desc&city=%s&scrollId=%s", city , scrollId);
                    HttpEntity<String> requestEntity = new HttpEntity<>(headers);
                    responseEntity = new RestTemplate().exchange(apiUrl, HttpMethod.GET, requestEntity, String.class);
                    String jsonPart = responseEntity.getBody();

                    jsonNode = objectMapper.readTree(jsonPart);
                }


                if (responseEntity.getStatusCode() == HttpStatus.OK) {

                    LocalDateTime saveDateTime = LocalDateTime.now();
                    String siteName = "snapp.doctor";

                    JsonNode doctors = jsonNode.get("data").get("expertsClinics");
                    scrollId = jsonNode.get("data").get("scrollId").asText();
                    System.out.println(scrollId);

                    for (JsonNode data : doctors) {
                        ObjectNode jsonPartNode = objectMapper.createObjectNode();
                        jsonPartNode.put("Date", saveDateTime.toString());
                        jsonPartNode.put("SiteName", siteName);

                        jsonPartNode.setAll((ObjectNode) data);

                        String jsonPart2 = jsonPartNode.toString();


                        mongoClient.getDatabase("test").getCollection("SnappAppointment").insertOne(Document.parse(jsonPart2));

                    }
                    if (isFirstTime){
                        isFirstTime = false;
                    }

                    Thread.sleep(1000);
                    
                }
            }while (!Objects.equals(scrollId, "null"));

        }



        DoctorOutputDto doctorOutputDto = new DoctorOutputDto();

        return doctorOutputDto;


    }


}

