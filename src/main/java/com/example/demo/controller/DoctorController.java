package com.example.demo.controller;

import com.example.demo.dto.DoctorOutputDto;
import com.example.demo.service.DoctorService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController()
@RequestMapping("/doctor")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorService doctorService;


    @PostMapping(value = "/doctor-info")
    public @ResponseBody
    DoctorOutputDto getCarInfoDetailFromResource() throws JsonProcessingException, InterruptedException {
        return doctorService.getdoctorinfobycity();
    }
}