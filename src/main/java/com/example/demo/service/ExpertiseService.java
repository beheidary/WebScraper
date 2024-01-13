package com.example.demo.service;


import com.example.demo.dto.OutDto.CityOutDto;
import com.example.demo.dto.OutDto.ExpertiseOutDto;
import com.example.demo.entity.CityEntity;

import com.example.demo.jpa.ExpertiseMongoRepository;
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
public class ExpertiseService {


    private final ExpertiseMongoRepository expertiseMongoRepository;

    private final MongoClient mongoClient;
    private final String apiUrl = "https://cyclops.drnext.ir/v1/website/search/filters";

    private final ObjectMapper objectMapper;
    private  final ExpertiseMapper expertiseMapper;


    public List<ExpertiseOutDto> catchExpertise() throws JsonProcessingException, InterruptedException {


        HttpHeaders headers = new HttpHeaders();
        headers.set("accept", "*/*");
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("accept-language", "en-US,en;q=0.5");
        headers.set("Accept-Encoding","gzip, deflate, br");
        headers.set("Referer","https://drdr.ir/search/expertise/");
        headers.set("origin", "https://drnext.ir");
        headers.set("referer", "https://drnext.ir/");
        headers.set("sec-ch-ua", "\"Not_A Brand\";v=\"8\", \"Chromium\";v=\"120\", \"Google Chrome\";v=\"120\"");
        headers.set("sec-ch-ua-mobile", "?0");
        headers.set("sec-ch-ua-platform", "\"Windows\"");
        headers.set("sec-fetch-dest", "empty");
        headers.set("sec-fetch-mode", "cors");
        [{"key":"Referer","value":"https://drdr.ir/search/expertise/","enabled":true},{"key":"x-nextjs-data","value":"1","enabled":true},{"key":"Connection","value":"keep-alive","enabled":true},{"key":"Cookie","value":"_gcl_au=1.1.1581063780.1703925183; analytics_campaign={%22source%22:%22google%22%2C%22medium%22:%22organic%22}; analytics_token=3ada5a57-4dc5-dea0-7115-231f1b544532; _ga_VPMXG7C0RF=GS1.1.1705132451.8.0.1705132451.0.0.0; _ga=GA1.2.611770857.1703925183; _yngt=408afe0d-dedf8-8742d-d2562-2955f3533afda; _clck=1dy59ke%7C2%7Cfid%7C0%7C1459; _gid=GA1.2.200130490.1705126841; yektanet_session_last_activity=1/13/2024; _yngt_iframe=1; _clsk=b7hrzk%7C1705130727784%7C4%7C1%7Cw.clarity.ms%2Fcollect","enabled":true},{"key":"Sec-Fetch-Dest","value":"empty","enabled":true},{"key":"Sec-Fetch-Mode","value":"cors","enabled":true},{"key":"Sec-Fetch-Site","value":"same-origin","enabled":true},{"key":"If-None-Match","value":"\"2rytpjhb5b718p\"","enabled":true},{"key":"TE","value":"trailers","enabled":true}]
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
