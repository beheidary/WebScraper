package com.example.demo.controller;

import com.example.demo.dto.OutDto.CityOutDto;
import com.example.demo.service.CityService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.List;


@RestController()
@RequestMapping("/City")
@RequiredArgsConstructor
public class CityController {

    private final CityService cityService;

    @PostMapping(value = "/city-add")
    public @ResponseBody DeferredResult<ResponseEntity<?>> addCity() throws JsonProcessingException, InterruptedException {
        DeferredResult<ResponseEntity<?>> result = new DeferredResult<>();

        List<CityOutDto> cities = cityService.addCity();

        result.setResult(ResponseEntity.status(HttpStatus.CREATED).body(cities));

        return result;
    }
}
