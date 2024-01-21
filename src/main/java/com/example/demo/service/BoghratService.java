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
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class BoghratService {


    private final ExpertiseMongoRepository expertiseMongoRepository;

    private final ObjectMapper objectMapper;
    private final MongoClient mongoClient;
    private final String apiUrl = "https://boghrat.com/api/SearchDoctor/GetDoctors";



    public DoctorOutputDto boghratGetDoctors() throws JsonProcessingException, InterruptedException {


        HttpHeaders headers = new HttpHeaders();
        headers.set("authority", "cyclops.drnext.ir");
        headers.set("accept", "application/json, text/plain, */*");
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("accept-language", "fa");
        headers.set("origin", "https://boghrat.com");
        headers.set("referer", "https://boghrat.com/search/doctors/%D8%AC%D8%B3%D8%AA%D8%AC%D9%88%DB%8C-%D9%BE%D8%B2%D8%B4%DA%A9%D8%A7%D9%86/?page=3");
        headers.set("sec-ch-ua", "\"Not_A Brand\";v=\"8\", \"Chromium\";v=\"120\", \"Google Chrome\";v=\"120\"");
        headers.set("sec-ch-ua-mobile", "?0");
        headers.set("sec-ch-ua-platform", "\"Windows\"");
        headers.set("sec-fetch-dest", "empty");
        headers.set("sec-fetch-mode", "cors");







                String requestBody = "{\"page\":1,\"name\":\"\",\"price\":null,\"campaign\":null,\"medicalNo\":null,\"province\":null,\"provinceTitle\":null,\"city\":null,\"cityTitle\":null,\"types\":[],\"pageNumber\":1,\"pageLength\":50000,\"expertises\":[],\"expertisesId\":[],\"visitTypes\":[],\"campaignIds\":[]}";
                HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
                ResponseEntity<String> responseEntity = new RestTemplate().exchange(apiUrl, HttpMethod.POST, requestEntity, String.class);
                String responseBody = responseEntity.getBody();

                    JsonNode jsonNode = objectMapper.readTree(responseBody);


                if (responseEntity.getStatusCode() == HttpStatus.OK) {
                    LocalDateTime saveDateTime = LocalDateTime.now();
                    String siteName = "boghrat.com";

                    JsonNode doctors = jsonNode.get("items");

                    for (JsonNode data : doctors) {
                        ObjectNode jsonPartNode = objectMapper.createObjectNode();
                        jsonPartNode.put("Date", saveDateTime.toString());
                        jsonPartNode.put("SiteName", siteName);

                        jsonPartNode.setAll((ObjectNode) data);

                        String jsonPart2 = jsonPartNode.toString();



                        mongoClient.getDatabase("test").getCollection("boghratDoctors").insertOne(Document.parse(jsonPart2));

                    }



                }



        DoctorOutputDto doctorOutputDto = new DoctorOutputDto();

        return doctorOutputDto;


    }


}
