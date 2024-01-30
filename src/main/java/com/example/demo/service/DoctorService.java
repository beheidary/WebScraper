package com.example.demo.service;

import com.example.demo.dto.DoctorOutputDto;
import com.example.demo.jpa.DoctorMongoRepository;
import com.example.demo.jpa.ExpertiseMongoRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.mongodb.client.MongoClient;
import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
//import com.example.demo.jpa.CityMongoRepository;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class DoctorService {


    private final ExpertiseMongoRepository expertiseMongoRepository;

    private final ObjectMapper objectMapper;
    private final MongoClient mongoClient;
    private final String apiUrl = "https://cyclops.drnext.ir/v1/website/search";



    public DoctorOutputDto getdoctorinfobycity() throws JsonProcessingException, InterruptedException {


        HttpHeaders headers = new HttpHeaders();
        headers.set("authority", "cyclops.drnext.ir");
        headers.set("accept", "application/json, text/plain, */*");
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("accept-language", "fa");
        headers.set("origin", "https://drnext.ir");
        headers.set("referer", "https://drnext.ir/");
        headers.set("sec-ch-ua", "\"Not_A Brand\";v=\"8\", \"Chromium\";v=\"120\", \"Google Chrome\";v=\"120\"");
        headers.set("sec-ch-ua-mobile", "?0");
        headers.set("sec-ch-ua-platform", "\"Windows\"");
        headers.set("sec-fetch-dest", "empty");
        headers.set("sec-fetch-mode", "cors");

        int index = 1;

        List<String> Expertise = expertiseMongoRepository.findAllByIdNotNull();




        for (String expert : Expertise) {
            int cityPage = 1;
            JsonNode jsonNode1 = objectMapper.readTree(expert);
            String id = jsonNode1.path("_id").asText();
            String nextPageValue = null;

            do {
                String requestBody = "{\"sort\":{\"name\":\"sortFreeTimeValue\",\"order\":\"ASC\"},\"query\":\"\",\"page\":"+ cityPage + ",\"speciality\":\"" +  id + "\",\"size\":" + 1000 + ",\"officeType\":[],\"gender\":[]}";
                System.out.println(requestBody);
                HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
                ResponseEntity<String> responseEntity = new RestTemplate().exchange(apiUrl, HttpMethod.POST, requestEntity, String.class);
                String responseBody = responseEntity.getBody();

                try {
                    JsonNode jsonNode = objectMapper.readTree(responseBody);
                    nextPageValue = jsonNode.get("meta").get("nextPage").asText();

                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (responseEntity.getStatusCode() == HttpStatus.OK) {
                    LocalDateTime saveDateTime = LocalDateTime.now();
                    String siteName = "drnext.ir";
                    JsonNode jsonNode3 = objectMapper.readTree(String.valueOf(responseBody));
                    if(jsonNode3.has("data") && jsonNode3.get("data").isArray() && jsonNode3.get("data").size() != 0) {
                        responseBody = "{\"Id\":" + index + ",\"Date\":\"" + saveDateTime + "\",\"SIteName\":\"" + siteName + "\",\"Data\":" + responseBody + "}";
                        mongoClient.getDatabase("test").getCollection("doctorsByCity").insertOne(Document.parse(responseBody));
                    }
                    index++;
                    cityPage++;

                    if (cityPage == 11 || nextPageValue == "null"){
                        nextPageValue=null;
                    }


                }
            } while (nextPageValue != null);


            Thread.sleep(1000);

            if (Objects.equals(id, "general-practitioner")){
                cityPage = 1;

                do {
                    String requestBody = "{\"sort\":{\"name\":\"sortFreeTimeValue\",\"order\":\"DESC\"},\"query\":\"\",\"page\":"+ cityPage + ",\"speciality\":\"" +  id + "\",\"size\":" + 1000 + ",\"officeType\":[],\"gender\":[]}";
                    System.out.println(requestBody);
                    HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
                    ResponseEntity<String> responseEntity = new RestTemplate().exchange(apiUrl, HttpMethod.POST, requestEntity, String.class);
                    String responseBody = responseEntity.getBody();

                    try {
                        JsonNode jsonNode = objectMapper.readTree(responseBody);
                        nextPageValue = jsonNode.get("meta").get("nextPage").asText();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    if (responseEntity.getStatusCode() == HttpStatus.OK) {
                        LocalDateTime saveDateTime = LocalDateTime.now();
                        String siteName = "drnext.ir";
                        JsonNode jsonNode3 = objectMapper.readTree(String.valueOf(responseBody));
                        if(jsonNode3.has("data") && jsonNode3.get("data").isArray() && jsonNode3.get("data").size() != 0) {
                            responseBody = "{\"Id\":" + index + ",\"Date\":\"" + saveDateTime + "\",\"SIteName\":\"" + siteName + "\",\"Data\":" + responseBody + "}";
                            mongoClient.getDatabase("test").getCollection("doctorsByCity").insertOne(Document.parse(responseBody));
                        }
                        index++;
                        cityPage++;

                        if (cityPage == 11 || nextPageValue == "null"){
                            nextPageValue=null;
                        }


                    }
                } while (nextPageValue != null);


            }
        }

        DoctorOutputDto doctorOutputDto = new DoctorOutputDto();

        return doctorOutputDto;


    }


}
