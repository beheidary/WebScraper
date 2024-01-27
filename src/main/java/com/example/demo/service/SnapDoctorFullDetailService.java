package com.example.demo.service;

import com.example.demo.dto.DoctorOutputDto;
import com.example.demo.jpa.ExpertiseMongoRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.*;
import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.TreeMap;

@Service
@RequiredArgsConstructor
public class SnapDoctorFullDetailService {



    private final ObjectMapper objectMapper;
    private final ObjectMapper objectMapper2;
    private final MongoClient mongoClient;
    private  ResponseEntity<String> responseEntity;
    private  ResponseEntity<String> responseEntity2;

    private String apiUrl = "";



    public DoctorOutputDto SnappFullDetail() throws JsonProcessingException, InterruptedException {


        HttpHeaders headers = new HttpHeaders();
        headers.set("accept", "application/json");
        headers.set("Content-Type", "application/json");
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("accept-language", "en-GB,en;q=0.5");
        headers.set("origin", "https://snapp.doctor");
        headers.set("referer", "https://snapp.doctor/");
        headers.set("sec-fetch-dest", "empty");
        headers.set("sec-fetch-mode", "cors");

        for (int index : getIndexs()) {

            apiUrl = String.format("https://core.snapp.doctor/Api/Common/v5/fetchExpert/expert_number/%d",index);
            HttpEntity<String> requestEntity = new HttpEntity<>(headers);
            try {
                responseEntity =   new RestTemplate().exchange(apiUrl, HttpMethod.GET, requestEntity, String.class);
            }catch (Exception ignored){}

        String responseBody = responseEntity.getBody();

        JsonNode jsonNode = objectMapper.readTree(responseBody);


        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            LocalDateTime saveDateTime = LocalDateTime.now();
            String siteName = "snapp.doctor";


            JsonNode data = jsonNode.get("data").get("expert");
            System.out.println(data.get("uuid"));
            System.out.println(data.get("stats").get("comment_count_number"));

            ObjectNode jsonPartNode = objectMapper.createObjectNode();
            try {
                jsonPartNode.put("Comments", getComments(data.get("uuid").asText(), data.get("stats").get("comment_count_number").asInt(), headers, objectMapper2));
                jsonPartNode.put("Date", saveDateTime.toString());
                jsonPartNode.put("SiteName", siteName);
                jsonPartNode.setAll((ObjectNode) data);
                String jsonPart2 = jsonPartNode.toString();
                mongoClient.getDatabase("test").getCollection("SnapDoctorFullDetail").insertOne(Document.parse(jsonPart2));
            }
            catch (Exception ignored){}

        }

    }



        DoctorOutputDto doctorOutputDto = new DoctorOutputDto();


        return doctorOutputDto;


    }

    private ArrayList<Integer> getIndexs (){

        ObjectMapper mapper = new ObjectMapper();
        ObjectReader reader = mapper.reader(JsonNode.class);
        ArrayList<Integer> numbers = new ArrayList<Integer>();



            String connectionString = "mongodb://admin:admin@localhost:27017/admin?authMechanism=SCRAM-SHA-256";
            MongoClientSettings settings = MongoClientSettings.builder()
                    .applyConnectionString(new ConnectionString(connectionString))
                    .build();
            try (var mongoClient = MongoClients.create(settings)) {
                MongoDatabase database = mongoClient.getDatabase("test");

                MongoCollection<Document> collection = database.getCollection("SnappOnlineCounseling");

                FindIterable<Document> documents = collection.find();



                try (MongoCursor<Document> cursor = documents.iterator()) {
                    while (cursor.hasNext()) {
                        Document document = cursor.next();

                        JsonNode node = reader.readValue(document.toJson());
                        numbers.add(node.get("expert_num").asInt());

                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }



            return numbers;

    }

    private JsonNode getComments (String uuid , int count,HttpHeaders headers,ObjectMapper objectMapper2) throws JsonProcessingException {


        apiUrl = String.format("https://core.snapp.doctor/Api/MobileApi/v1/getExpertComments/%s/0/%d",uuid,count);
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        try {

            responseEntity2 = new RestTemplate().exchange(apiUrl, HttpMethod.GET, requestEntity, String.class);

        }
        catch (Exception ignore){};
        String responseBody = responseEntity2.getBody();

        JsonNode jsonNode = objectMapper2.readTree(responseBody);

        return jsonNode;


    }



}

