package com.example.demo.service;

import com.example.demo.dto.OutDto.CityOutDto;
import com.example.demo.entity.CityEntity;
import com.example.demo.jpa.CityMongoRepository;
import com.example.demo.mapper.CityMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.mongodb.client.MongoClient;
import lombok.RequiredArgsConstructor;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.bson.Document;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CityService {

    private final CityMongoRepository cityMongoRepository;

    private final MongoClient mongoClient;
    private final String apiUrl = "https://cyclops.drnext.ir/v1/website/search/filters";

    private final ObjectMapper objectMapper;
    private  final CityMapper cityMapper;


    public List<CityOutDto> addCity() throws JsonProcessingException, InterruptedException {


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
        int arrindex = 0;
        

            HttpEntity<String> requestEntity = new HttpEntity<>(headers);

            ResponseEntity<String> responseEntity = new RestTemplate().exchange(apiUrl, HttpMethod.GET, requestEntity, String.class);


            JsonNode jsonNode = objectMapper.readTree(String.valueOf(responseEntity.getBody()));
            JsonNode filters = jsonNode.get("data").get("filters");
            JsonNode items = filters.get(1).get("items");
            List<CityOutDto> cityOutDtos = new ArrayList<CityOutDto>();

            for ( JsonNode item : items) {
    
                    CityEntity cityEntity = new CityEntity();
                    cityEntity.setName(item.get("name").asText());
                    cityEntity.setLatinName(item.get("id").asText());

                cityOutDtos.add(arrindex,cityMapper.entityToDto(cityMongoRepository.save(cityEntity)));
    
    
                }

        return cityOutDtos;


    }


}
