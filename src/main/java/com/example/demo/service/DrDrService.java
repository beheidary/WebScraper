package com.example.demo.service;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.example.demo.dto.DoctorOutputDto;
import com.example.demo.jpa.DoctorMongoRepository;
import com.example.demo.jpa.ExpertiseMongoRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mongodb.client.MongoClient;
import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import com.example.demo.jpa.CityMongoRepository;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DrDrService {

    private final DoctorMongoRepository doctorMongoRepository;
    private final ExpertiseMongoRepository expertiseMongoRepository;
    private final ObjectMapper objectMapper;
    private final MongoClient mongoClient;
    private JsonNode jsonNode;


    public DoctorOutputDto GetDoctorInfoByExpertise() throws JsonProcessingException, InterruptedException {


        HttpHeaders headers = new HttpHeaders();

        List<String> Expertise = expertiseMongoRepository.findAllByIdNotNull();


        for (String expertise : Expertise) {
            int current_page = 1;
            JsonNode jsonNode1 = objectMapper.readTree(expertise);
            int expertiseId = jsonNode1.path("_id").asInt();
            System.out.println(expertiseId);
            int last_page = 0;

            do {
                String apiUrl = String.format("https://drdr.ir/_next/data/JpGQC_3c-Fyfj3i7wJgP7/search/expertise-%d.json?page=%d", expertiseId, current_page);
                HttpEntity<String> requestEntity = new HttpEntity<>(headers);
                ResponseEntity<String> responseEntity = new RestTemplate().exchange(apiUrl, HttpMethod.GET, requestEntity, String.class);
                String jsonPart = responseEntity.getBody();

                try{
                    jsonNode = objectMapper.readTree(jsonPart);

                }catch (Exception e){
                    jsonPart = extractJsonFromScript(jsonPart);
                    jsonNode = objectMapper.readTree(jsonPart);
                    jsonNode = jsonNode.get("props");
                }


                if (current_page == 1 ){

                    try {
                        last_page = jsonNode.get("pageProps").get("drsListPagination").get("last_page").asInt();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                System.out.println(last_page);
                System.out.println(current_page);

                if (responseEntity.getStatusCode() == HttpStatus.OK) {
                    LocalDateTime saveDateTime = LocalDateTime.now();
                    String siteName = "drdr.ir";

                    JsonNode drsListWithPromotion = jsonNode.get("pageProps").get("drsListWithPromotion");

                    for (JsonNode data : drsListWithPromotion) {
                        ObjectNode jsonPartNode = objectMapper.createObjectNode();
                        jsonPartNode.put("Date", saveDateTime.toString());
                        jsonPartNode.put("SIteName", siteName);

                        jsonPartNode.setAll((ObjectNode) data);

                        String jsonPart2 = jsonPartNode.toString();

                        mongoClient.getDatabase("test").getCollection("doctorsByExpertise").insertOne(Document.parse(jsonPart2));
                    }
                }

                current_page++;


            } while (current_page <= last_page);

        }


        DoctorOutputDto doctorOutputDto = new DoctorOutputDto();

        return doctorOutputDto;


    }

    private String extractJsonFromScript(String responseBody) {
        String scriptId = "__NEXT_DATA__";
        String startPattern = String.format("<script id=\"%s\" type=\"application/json\">", scriptId);
        String endPattern = "</script>";

        int startIndex = responseBody.indexOf(startPattern);
        int endIndex = responseBody.indexOf(endPattern, startIndex);

        if (startIndex != -1 && endIndex != -1) {
            return responseBody.substring(startIndex + startPattern.length(), endIndex).trim();
        } else {
            return null;
        }

    }
}