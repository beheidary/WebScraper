package com.example.demo.service;


import com.example.demo.dto.OutDto.ExpertiseOutDto;
import com.example.demo.entity.ExpertiseEntity;
import com.example.demo.jpa.ExpertiseMongoRepository;
import com.example.demo.mapper.ExpertiseMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.mongodb.client.MongoClient;
import lombok.RequiredArgsConstructor;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpertiseService {


    private final ExpertiseMongoRepository expertiseMongoRepository;

    private final MongoClient mongoClient;
    private final String apiUrl = "https://drdr.ir/_next/data/JpGQC_3c-Fyfj3i7wJgP7/search/expertise.json?slug=expertise";

    private final ObjectMapper objectMapper;
    private  final ExpertiseMapper expertiseMapper;


    public List<ExpertiseOutDto> catchExpertise() throws JsonProcessingException, InterruptedException {


        HttpHeaders headers = new HttpHeaders();
        int arrindex = 0;


        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<String> responseEntity = new RestTemplate().exchange(apiUrl, HttpMethod.GET, requestEntity, String.class);


        JsonNode jsonNode = objectMapper.readTree(String.valueOf(responseEntity.getBody()));
        JsonNode items = jsonNode.get("pageProps").get("seoFooter").get("specialities");
        List<ExpertiseOutDto> expertiseOutDtos = new ArrayList<ExpertiseOutDto>();

        for ( JsonNode item : items) {

            ExpertiseEntity expertiseEntity = new ExpertiseEntity();
            expertiseEntity.setId(item.get("id").asText());
            expertiseEntity.setSlug(item.get("slug").asText());
            expertiseEntity.setTitle(item.get("title").asText());

            expertiseOutDtos.add(arrindex,expertiseMapper.entityToDto(expertiseMongoRepository.save(expertiseEntity)));


        }

        return expertiseOutDtos;


    }
}
