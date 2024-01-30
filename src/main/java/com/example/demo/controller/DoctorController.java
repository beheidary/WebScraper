package com.example.demo.controller;

import com.example.demo.dto.DoctorOutputDto;
import com.example.demo.service.*;
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
    private final BoghratService boghratService;
    private final SnappAppointmentService snappAppointmentService;
    private final SnappOnlineCounselingService snappOnlineCounselingService;
    private final SnapDoctorFullDetailService snapDoctorFullDetailService;
    private final DoctorCollectionUpdate doctorCollectionUpdate;


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

    @PostMapping(value = "/doctor-info-boghrat")
    public @ResponseBody
    DoctorOutputDto GetDoctorInfoboghrat() throws JsonProcessingException, InterruptedException {
        return boghratService.boghratGetDoctors();
    }

    @GetMapping(value = "/doctor-SnappAppointment")
    public @ResponseBody
    DoctorOutputDto SnappAppointment() throws JsonProcessingException, InterruptedException {
        return snappAppointmentService.GetSnappAppointmentdoctors();
    }
    @PostMapping(value = "/doctor-OnlineCounseling")
    public @ResponseBody
    DoctorOutputDto SnappOnlineCounseling() throws JsonProcessingException, InterruptedException {
        return snappOnlineCounselingService.GetOnlineCounselingDoctors();
    }

    @GetMapping(value = "/doctor-SnappFull")
    public @ResponseBody
    DoctorOutputDto SnappFull() throws JsonProcessingException, InterruptedException {
        return snapDoctorFullDetailService.SnappFullDetail();
    }

    public String innerUpdate() throws JsonProcessingException, InterruptedException {

        System.out.println(doctorCollectionUpdate.DoctorkUpdate());

        return "in controller";
    }
}