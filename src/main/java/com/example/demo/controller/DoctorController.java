package com.example.demo.controller;

import com.example.demo.dto.DoctorOutputDto;
import com.example.demo.service.DoctorService;
import com.example.demo.service.DoctoretoService;
import com.example.demo.service.DrDrService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController()
@RequestMapping("/doctor")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorService doctorService;
    private final DrDrService drdrService;
    private final DoctoretoService doctoretoService;


    @PostMapping(value = "/doctor-info-drnext")
    public @ResponseBody
    DoctorOutputDto GetDoctorInfoDrNext() throws JsonProcessingException, InterruptedException {
        return doctorService.getdoctorinfobycity();
    }

    @GetMapping(value = "/doctor-info-drdr")
    public @ResponseBody
    DoctorOutputDto GetDoctorInfoDrDr() throws JsonProcessingException, InterruptedException {
        return drdrService.GetDoctorInfoByExpertise();
    }

    @GetMapping(value = "/doctor-info-doctoreto")
    public @ResponseBody
    DoctorOutputDto GetDoctorInfoDrTo() throws JsonProcessingException, InterruptedException {
        return doctoretoService.GetDoctorInfoWithoutFilter();
    }
}