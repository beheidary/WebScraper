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
public class DoctoretoService {

    private final DoctorMongoRepository doctorMongoRepository;
    private final ExpertiseMongoRepository expertiseMongoRepository;
    private final ObjectMapper objectMapper;
    private final MongoClient mongoClient;
    private JsonNode jsonNode;


    public DoctorOutputDto GetDoctorInfoWithoutFilter() throws JsonProcessingException, InterruptedException {


        HttpHeaders headers = new HttpHeaders();


            int current_page = 6737;
            int last_page = 8593;
            int index = 113421;

            do {
                String apiUrl = String.format("https://doctoreto.com/_next/data/qGj1syEjXlEPJbDLqqlWF/doctors.json?page=%d", current_page);
                HttpEntity<String> requestEntity = new HttpEntity<>(headers);
                ResponseEntity<String> responseEntity = new RestTemplate().exchange(apiUrl, HttpMethod.GET, requestEntity, String.class);
                String jsonPart = responseEntity.getBody();


                    jsonNode = objectMapper.readTree(jsonPart);



                System.out.println(last_page);
                System.out.println(current_page);

                if (responseEntity.getStatusCode() == HttpStatus.OK) {
                    LocalDateTime saveDateTime = LocalDateTime.now();
                    String siteName = "doctoreto.ir";

                    JsonNode doctors = jsonNode.get("pageProps").get("dehydratedState").get("queries").get(2).get("state").get("data").get("data");

                    for (JsonNode data : doctors) {
                        ObjectNode jsonPartNode = objectMapper.createObjectNode();
                        jsonPartNode.put("Id" , index );
                        jsonPartNode.put("Date", saveDateTime.toString());
                        jsonPartNode.put("SIteName", siteName);

                        jsonPartNode.setAll((ObjectNode) data);

                        String jsonPart2 = jsonPartNode.toString();



                        mongoClient.getDatabase("test").getCollection("DoctoreToSec3").insertOne(Document.parse(jsonPart2));

                        index++;
                    }

                }

                current_page++;


            } while (current_page <= last_page);




        DoctorOutputDto doctorOutputDto = new DoctorOutputDto();

        return doctorOutputDto;


    }

}