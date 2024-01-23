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
public class SnappOnlineCounselingService {


    private final ObjectMapper objectMapper;
    private final MongoClient mongoClient;
    private final String apiUrl = "https://snapp.doctor/webapp/appointment/expertsByCity/";
    private final String[] searchedTerms = {"/speciality/neurologist/","/speciality/obstetrician-gynecologist/","/speciality/internal-medicine/","/speciality/psychiatrist/","/speciality/general-practitioner/","/speciality/pediatric/","/speciality/urologist/","/speciality/dermatologist/","/speciality/orthopedist/","/speciality/corona-virus/","/speciality/otorhinolaryngologist/","/speciality/sexual-health/","/speciality/cardiologist/","/speciality/hematology-oncology/","/speciality/dietitians-nutritionist/","/speciality/ophthalmology/","/speciality/doctor-dental-surgery/","/speciality/midwifery/","/speciality/general-surgeon/","/speciality/pharmacist/","/speciality/geneticist/","/speciality/traditional-medicine/","/speciality/anesthesiologist/","/speciality/medical-ultrasound-radiologist/","/speciality/emergency-medicine/","/speciality/physical-medicine-rehabilitation/","/speciality/cosmetic-plastic-surgery/"};



    public DoctorOutputDto GetOnlineCounselingDoctors() throws JsonProcessingException, InterruptedException {


        HttpHeaders headers = new HttpHeaders();
        headers.set("accept", "application/json");
        headers.set("Content-Type","application/json");
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("accept-language", "en-GB,en;q=0.5");
        headers.set("origin", "https://snapp.doctor");
        headers.set("referer", "https://snapp.doctor/");
        headers.set("sec-fetch-dest", "empty");
        headers.set("sec-fetch-mode", "cors");



        for (String searchedTerm : searchedTerms) {

            JsonNode jsonNode;
            String scrollId = "";

            boolean isFirstTime = true;
            ResponseEntity<String> responseEntity;

            do {

                if (isFirstTime) {

                    String requestBody = "{\"searchedTerm\":\""+searchedTerm+"\",\"filters\":\"all\"}";
                    String apiUrl = "https://core.snapp.doctor/Api/Common/v4/fetchExpertsByCategoryIdOrUrl?&excludeReservationExperts=1";
                    HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
                    responseEntity = new RestTemplate().exchange(apiUrl, HttpMethod.POST, requestEntity, String.class);
                    String jsonPart = responseEntity.getBody();

                    jsonNode = objectMapper.readTree(jsonPart);

                } else {

                    String requestBody = "{\"searchedTerm\":\""+searchedTerm+"\",\"filters\":\"all\"}";
                    String apiUrl = String.format("https://core.snapp.doctor/Api/Common/v4/fetchExpertsByCategoryIdOrUrl?&scrollId=%s&excludeReservationExperts=1", scrollId);
                    HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
                    responseEntity = new RestTemplate().exchange(apiUrl, HttpMethod.POST, requestEntity, String.class);
                    String jsonPart = responseEntity.getBody();

                    jsonNode = objectMapper.readTree(jsonPart);
                }


                if (responseEntity.getStatusCode() == HttpStatus.OK) {

                    LocalDateTime saveDateTime = LocalDateTime.now();
                    String siteName = "snapp.doctor";

                    JsonNode doctors = jsonNode.get("data").get("experts");
                    try{
                        scrollId = jsonNode.get("data").get("scrollId").asText();
                    }catch (Exception e){
                        scrollId = "null";
                    }
                    System.out.println(scrollId);

                    for (JsonNode data : doctors) {
                        ObjectNode jsonPartNode = objectMapper.createObjectNode();
                        jsonPartNode.put("Date", saveDateTime.toString());
                        jsonPartNode.put("SiteName", siteName);

                        jsonPartNode.setAll((ObjectNode) data);

                        String jsonPart2 = jsonPartNode.toString();


                        mongoClient.getDatabase("test").getCollection("SnappOnlineCounseling").insertOne(Document.parse(jsonPart2));

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

