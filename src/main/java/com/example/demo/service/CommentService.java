package com.example.demo.service;

import com.example.demo.dto.DoctorOutputDto;
import com.example.demo.jpa.DoctorMongoRepository;
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

import java.util.List;


@Service
@RequiredArgsConstructor
public class CommentService {


    private final ExpertiseMongoRepository expertiseMongoRepository;
    private final DoctorMongoRepository doctorMongoRepository;

    private final ObjectMapper objectMapper;
    private final MongoClient mongoClient;

    private int commentCount;
    private String targetId;
    private JsonNode jsonNode;
    private final String apiUrl = "https://cyclops.drnext.ir/v1/website/search";



    public DoctorOutputDto ExtractComments() throws JsonProcessingException, InterruptedException {


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
        int sumNumber = 0;
        int allPages = 0;
        int current_page =0;


        int index = 1;

        List<String> Expertise = doctorMongoRepository.findAllBycommentCountNotNull();




        for (String expert : Expertise) {

            jsonNode = objectMapper.readTree(expert);
            commentCount = jsonNode.path("commentCount").asInt();
            targetId = jsonNode.path("id").asText();
            sumNumber += commentCount;
            System.out.println("Total Comments Extracted: "+ sumNumber);
            if (commentCount > 1500){
                System.out.println("in bishtar bood");
            }



//            do {
                String apiUrl = String.format("https://cyclops.drnext.ir/v1/website/patients/comments?page=1&size=1500&targetId=%s&targetType=doctor&orderBy=createdAt&sortBy=desc", targetId);
                HttpEntity<String> requestEntity = new HttpEntity<>(headers);

                ResponseEntity<String> responseEntity = new RestTemplate().exchange(apiUrl, HttpMethod.GET, requestEntity, String.class);
                String jsonPart = responseEntity.getBody();



                if (responseEntity.getStatusCode() == HttpStatus.OK) {
                    String siteName = "drnext.ir";
                    jsonNode = objectMapper.readTree(String.valueOf(jsonPart)).get("data");


                    for (JsonNode data : jsonNode) {
                        ObjectNode jsonPartNode = objectMapper.createObjectNode();
                        jsonPartNode.put("SiteName", siteName);

                        jsonPartNode.setAll((ObjectNode) data);

                        String jsonPart2 = jsonPartNode.toString();

                        mongoClient.getDatabase("test").getCollection("doctorNextComments").insertOne(Document.parse(jsonPart2));
                    }


                }
//            } while (current_page <= allPages);


        }

        DoctorOutputDto doctorOutputDto = new DoctorOutputDto();

        return doctorOutputDto;


    }


}
